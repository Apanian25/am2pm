package com.dawson.jonat.stockers.CurrecnyExchange;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dawson.jonat.stockers.Menu.Menus;
import com.dawson.jonat.stockers.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Nicholas Apanian
 *
 */
public class CurrencyExchangeActivity extends Menus {

    private Spinner toCurrencySpinner, fromCurrencySpinner;
    private EditText amount;
    private String currency;
    private boolean reCalculateResult;
    ArrayList<String> currencies;
    ArrayList<Double> rates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(checkConnectivity()) {
            setContentView(R.layout.activity_currency_exchange);
        } else {
            setContentView(R.layout.error_page);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(checkConnectivity()) {
            instantiatePrivateFields();
            addTextChangedListenerToAmount();
        }
    }

    /**
     * Gets the
     *
     * @author Nicholas, Lara
     */
    private void getCurrency(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int currencyPosition = prefs.getInt("curr", 0);
        currency = getResources().getStringArray(R.array.spinner_values_currency)[currencyPosition];
    }


    /**
     * Instantiates the private fields for the Class to use.
     */
    private void instantiatePrivateFields() {
        if(checkConnectivity()) {
            getCurrency();
            amount = findViewById(R.id.amount);
            toCurrencySpinner = findViewById(R.id.to_currencies);
            fromCurrencySpinner = findViewById(R.id.from_currencies);
            reCalculateResult = false;
            currencies = new ArrayList<>();
            rates = new ArrayList<>();
            //Default USD to get all currencies to populate the Spinners
            queryTheAPI(currency);
        }

    }

    /**
     * Adds the TextChangedListener to the amount EditText
     * Whenever the text changes in the EditText, this
     * method will calculate the exchanged amount
     */
    private void addTextChangedListenerToAmount() {
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double doubleAmount;
                if(s.toString().isEmpty())
                    doubleAmount = 0.0;
                else
                    doubleAmount = Double.valueOf(s.toString());

                performExchange(doubleAmount);
            }
        });
    }

    /**
     * Exchanges a given amount of the users preferred currency
     * to the selected currency and displays it to the user
     *
     * @param doubleAmount the original amount to be exchanged
     */
    private void performExchange(Double doubleAmount) {
        Log.i("performExchange", toCurrencySpinner.getSelectedItem() + "");
        TextView exchangedAmount = findViewById(R.id.result);
        if(toCurrencySpinner.getSelectedItem() == null)
            return;
        Log.i("performExchange", "worked");
        int index = currencies.indexOf(toCurrencySpinner.getSelectedItem().toString());
        if(index >= 0)
            exchangedAmount.setText(
                String.format("%s", Math.round(doubleAmount * rates.get(index) * 100)/100.0));
    }

    /**
     * Creates the Spinner by populating it and assigning a Listener to it for when
     * the user changed the currency they would like to exchange to
     */
    private void createSpinners(){
        //create the array adapters using the pre-defined spinner layout in android

        toCurrencySpinner.setAdapter(createAdapter());
        fromCurrencySpinner.setAdapter(createAdapter());
        if(!currency.equals("BTC") && !currency.isEmpty()) {
            fromCurrencySpinner.setSelection(currencies.indexOf(currency));
        }

        toCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String value = amount.getText().toString();

                if(value.isEmpty())
                    performExchange(0.0);
                else
                    performExchange(Double.valueOf(value));

                ((TextView)findViewById(R.id.convertedCurrency)).
                        setText((String)toCurrencySpinner.getSelectedItem());

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        fromCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                reCalculateResult = true;
                if(checkConnectivity()) {
                    queryTheAPI(fromCurrencySpinner.getSelectedItem().toString());
                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**
     * create the array adapters using the pre-defined spinner layout in android
     *
     * @return ArrayAdapter<String>
     */
    private ArrayAdapter<String> createAdapter() {
        ArrayAdapter<String> adapter= new ArrayAdapter<>(getApplicationContext(),R.layout.text_view_for_spinner, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        return adapter;
    }

    /**
     * Asynchronous class used to query the Api for the available currencies and their rates.
     */
    private class getExchange extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                CurrencyExchangeApi api = new CurrencyExchangeApi();
                currencies = new ArrayList<>();
                rates = new ArrayList<>();
                return api.getAllCurrencies(strings[0], currencies, rates);
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if(toCurrencySpinner.getSelectedItem() == null && fromCurrencySpinner.getSelectedItem() == null) {
                //first time the Api is querried
                createSpinners();
            }

            if(reCalculateResult) {
                //update the exchanged value
                String value = amount.getText().toString();

                if(!value.isEmpty())
                    performExchange(Double.valueOf(value));
                reCalculateResult = !reCalculateResult;
            }
        }
    }

    /**
     * Runs the Async method to update the rates and the currencies
     */
    public void queryTheAPI(String currency) {
        String url = "https://api.exchangeratesapi.io/latest?base=" + currency;
        new getExchange().execute(url);
    }


    /**
     * Checks to see if the users device is connected to the internet or data
     * If the user is connected, it will call the Api to get all necessary information
     * for exchanging currencies and the currencies themselves
     * If the user is NOT connected, the user will be redirected to a 503 page.
     */
    private boolean checkConnectivity() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Connection is established
            return true;
        } else {
            //There is no Connection
            return false;
        }
    }
}
