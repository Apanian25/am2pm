package com.dawson.jonat.stockers;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

public class CurrencyExchange extends Activity {

    private TextView connectivity;
    private EditText amount;
    private Spinner currencySpinner;
    ArrayList<String> currencies;
    ArrayList<Double> rates;
    private static final String TAG = "CurrecnyExcahnge";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_exchange);

        instantiatePrivateFields();


    }

    /**
     * Instantiates the private fields for the Class to use.
     *
     */
    private void instantiatePrivateFields() {
        connectivity = (TextView) findViewById(R.id.connectivity);
        amount = (EditText) findViewById(R.id.amount);
        currencySpinner = (Spinner) findViewById(R.id.currencies);

        getExchangeRates();


        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double doubleAmount = Double.valueOf(s.toString());
                performExchange(doubleAmount);
            }

            private void performExchange(Double doubleAmount) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void createSpinner(){
        //create an array adapter using the pre-defined spinner layout in android
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, currencies);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        currencySpinner.setAdapter(adapter);
//        currencySpinner.setOnItemSelectedListener(this);
    }


    /**
     * Gets the Exchange rates and the currencies available from the Api
     */
    private void getExchangeRates() {
        //the base rate will change when using the user preferences
        String url = "https://api.exchangeratesapi.io/latest?base=USD";

        if(checkConnectivity()) {
            new getExchange().execute(url);
        }
    }


    /**
     * Asynchronous class used to query the Api for the available currencies and their rates.
     */
    private class getExchange extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                return getAllCurrencies(strings[0]);
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
     * Checks to see if the clients device is connected to the internet or data
     * @return Boolean
     */
    private boolean checkConnectivity() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Connection is established
            // invoke the AsyncTask to do the dirtywork.
            connectivity.setText("Connection available.");
            return true;
        } else {
            //There is no Connection
            connectivity.setText("No network connection available.");
            return false;
        }
    }


    /**
     * Queries the Api for the different currencies and rates available.
     *
     * @param urlText url to the Api
     * @return Boolean
     * @throws IOException
     */
    private boolean getAllCurrencies(String urlText) throws IOException {
        //input stream variable
        InputStream input = null;
        //httpConnection variable
        HttpURLConnection httpUrlCon = null;
        //create a new URL Object
        URL url = new URL(urlText);

        //try to make a connection
        try {
            httpUrlCon = setTheConnection(httpUrlCon, url);
            //check the if the connection was successful
            int response = httpUrlCon.getResponseCode();
            if (response != HttpURLConnection.HTTP_OK) {
                //the response didn't succeed
                return false;
            }
            //Response was OK
            //get the stream to the input
            Log.i(TAG, "Successful connection");

            input = httpUrlCon.getInputStream();

            JSONObject json = new JSONObject(convertResponseToString(input));
            JSONObject jaySon = json.getJSONObject("rates");

            currencies = new ArrayList<>();
            rates = new ArrayList<>();
            for(int i = 0; i < jaySon.names().length(); i++) {
                currencies.add(jaySon.names().getString(i));
                rates.add(Double.valueOf(jaySon.get(currencies.get(i)).toString()));
            }

            return true;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        httpUrlCon.disconnect();
        input.close();
        return false;
    }


    /**
     * Private helper method to create a a connection with the API
     *
     * @param httpURLConnection
     * @param url
     * @return
     * @throws IOException
     */
    private HttpURLConnection setTheConnection(HttpURLConnection httpURLConnection, URL url) throws IOException {
        //init
        httpURLConnection = (HttpURLConnection) url.openConnection();
        //sets all the field that will initialize the connection
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoInput(true); //allow the receive data
        httpURLConnection.connect();
        return httpURLConnection;
    }

    /**
     * Private helper method to get inputStream to json object
     *
     * @param input
     * @return String
     * @throws IOException
     */
    private String convertResponseToString(InputStream input) throws IOException {
        int bytesRead, totalRead=0;
        byte[] buffer = new byte[1024];

        // for data from the server
        BufferedInputStream bufferedInStream = new BufferedInputStream(input);
        // to collect data in our output stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream writer = new DataOutputStream(byteArrayOutputStream);

        // read the stream until end
        while ((bytesRead = bufferedInStream.read(buffer)) != -1) {
            writer.write(buffer);
            totalRead += bytesRead;
        }
        writer.flush();
        Log.d(TAG, "Bytes read: " + totalRead
                + "(-1 means end of reader so max of)");

        return byteArrayOutputStream.toString();
    }
}
