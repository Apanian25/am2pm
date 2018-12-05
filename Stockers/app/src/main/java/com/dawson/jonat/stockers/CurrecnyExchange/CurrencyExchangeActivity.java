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

    private Spinner currencySpinner;
    private EditText amount;
    private String currency;
    ArrayList<String> currencies;
    ArrayList<Double> rates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_currency_exchange);
        instantiatePrivateFields();
        addTextChangedListenerToAmount();
    }

    @Override
    public void onStart() {
        super.onStart();
        instantiatePrivateFields();
    }

    /**
     * Instantiates the private fields for the Class to use.
     */
    private void instantiatePrivateFields() {
        getCurrency();

        amount = findViewById(R.id.amount);
        //will be changed to users preference after
        ((TextView)findViewById(R.id.userCurrency)).setText(currency);
        currencySpinner = findViewById(R.id.currencies);
        currencies = new ArrayList<>();
        rates = new ArrayList<>();

        checkConnectivity();
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
        TextView exchangedAmount = findViewById(R.id.result);
        if(currencySpinner.getSelectedItem() == null)
            return;
        int index = currencies.indexOf(currencySpinner.getSelectedItem().toString());
        exchangedAmount.setText(
                String.format("%s", Math.round(doubleAmount * rates.get(index) * 100)/100.0));
    }

    /**
     * Creates the Spinner by populating it and assigning a Listener to it for when
     * the user changed the currency they would like to exchange to
     */
    private void createSpinner(){
        //create an array adapter using the pre-defined spinner layout in android
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,R.layout.text_view_for_spinner, currencies);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        currencySpinner.setAdapter(adapter);
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String value = amount.getText().toString();

                if(value.isEmpty())
                    performExchange(0.0);
                else
                    performExchange(Double.valueOf(value));

                ((TextView)findViewById(R.id.convertedCurrency)).
                        setText((String)currencySpinner.getSelectedItem());

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**
     * Asynchronous class used to query the Api for the available currencies and their rates.
     */
    private class getExchange extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                CurrencyExchangeApi api = new CurrencyExchangeApi();
                return api.getAllCurrencies(strings[0], currencies, rates);
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            createSpinner();
        }
    }


    /**
     * Checks to see if the users device is connected to the internet or data
     * If the user is connected, it will call the Api to get all necessary information
     * for exchanging currencies and the currencies themselves
     * If the user is NOT connected, the user will be redirected to a 503 page.
     */
    private void checkConnectivity() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Connection is established
            //the base rate will change when using the user preferences
            String url = "https://api.exchangeratesapi.io/latest?base=" + currency;
            new getExchange().execute(url);
        } else {
            //There is no Connection
            //redirect to the 503 that Lara is creating
        }
    }
}
