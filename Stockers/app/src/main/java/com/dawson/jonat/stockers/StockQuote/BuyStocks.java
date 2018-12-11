package com.dawson.jonat.stockers.StockQuote;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Nicholas Apanian, Lara Mezirovsky
 * @version 1.0.0
 */
public class BuyStocks extends AsyncTask<String, Void, String> {

    private final ShowStockActivity stockActivity;
    private int quantity;
    private String ticker;

    /**
     * Constructor that sets the private fields.
     *
     * @param stockActivity context
     * @param quantity the amount of stocks to buy
     * @param ticker ticker symbol for the company
     */
    public BuyStocks(ShowStockActivity stockActivity, String quantity, String ticker) {
        this.stockActivity = stockActivity;
        this.quantity = Integer.valueOf(quantity);
        this.ticker = ticker;
    }

    /**
     * Creates the connection an configures it
     *
     * @param address url
     * @param token verification token
     * @return conn HttpURLConnection
     * @throws IOException
     */
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
        return "";
    }

    @Override
    protected void onPostExecute(String status) {
        super.onPostExecute(status);
        if(status.equals("success!")) {
            stockActivity.setBalance();
        }
    }
}
