package com.dawson.jonat.stockers.StockQuote;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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

public class BuyStocks extends AsyncTask<String, Void, String> {

    private final ShowStockActivity stockActivity;
    private int quantity;
    private String ticker, balance;

    public BuyStocks(ShowStockActivity stockActivity, String quantity, String ticker) {
        this.stockActivity = stockActivity;
        this.quantity = Integer.valueOf(quantity);
        this.ticker = ticker;
    }

    public HttpURLConnection createConnection(String address, String token) throws IOException {
        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        return conn;
    }
    @Override
    protected String doInBackground(String ... credentials) {

        try {
            //process token
            HttpURLConnection conn = createConnection("http://stockers-web-app.herokuapp.com/api/api/buy", credentials[0]);
            JSONObject json = new JSONObject();
            json.put("quantity", quantity);
            json.put("ticker", ticker);

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(json.toString());
            out.flush();
            out.close();
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = conn.getInputStream();
                balance = (new JSONObject(convertResponseToString(in))).getString("cashleft");

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(stockActivity);
                SharedPreferences.Editor editor = prefs.edit();
                //parse the balance
                editor.putString("balance" , balance);
                editor.commit();


                return "success!";
            } else if (conn.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
               return "Invalid ticker or quantity";
                //display the error message to the user
            } else if (conn.getResponseCode() == HttpURLConnection.HTTP_FORBIDDEN) {
                return "Insufficient cash or too many stocks owned";
                //display the error message to the user
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return ""; //todo to show the user the status of the request
    }

    @Override
    protected void onPostExecute(String status) {
        super.onPostExecute(status);
        if(status.equals("success!"))
            stockActivity.setBalance(balance);
    }

    /**
     * Private helper method to get inputStream to json object
     *
     * @author Patricia Campbell, faculty at Dawson College in the Computer
     * Science departement todo: add git lab repo link
     */
    private String convertResponseToString(InputStream input) throws IOException {
        int bytesRead, totalRead = 0;
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
        Log.d("hello", "Bytes read: " + totalRead
                + "(-1 means end of reader so max of)");

        return byteArrayOutputStream.toString();

    }
}
