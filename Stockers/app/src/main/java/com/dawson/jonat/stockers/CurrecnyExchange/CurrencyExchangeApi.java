package com.dawson.jonat.stockers.CurrecnyExchange;

import android.util.Log;

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
import java.util.Collections;

/**
 * @author Nicholas Apanian
 */
class CurrencyExchangeApi {

    private static final String TAG = "CurrencyExApi";

    public CurrencyExchangeApi() {

    }

    /**
     * Private helper method to create a a connection with the API
     *
     * @param httpURLConnection
     * @param url
     * @return HttpUrlConnection
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
     * Queries the Api for the different currencies and rates available.
     *
     * @param urlText url to the Api
     * @return Boolean
     */
    boolean getAllCurrencies(String urlText, ArrayList<String> currencies, ArrayList<Double> rates) throws IOException {
        InputStream input = null;
        HttpURLConnection httpUrlCon = null;
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

            //Two for loops needed to have the currencies sorted
            for(int i = 0; i < jaySon.names().length(); i++) {
                currencies.add(jaySon.names().getString(i));
            }

            Collections.sort(currencies);

            for(int i = 0; i < jaySon.names().length(); i++) {
                rates.add(Double.valueOf(jaySon.get(currencies.get(i)).toString()));
            }

            return true;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        if(httpUrlCon != null)
            httpUrlCon.disconnect();
        if(input != null)
            input.close();
        return false;
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
