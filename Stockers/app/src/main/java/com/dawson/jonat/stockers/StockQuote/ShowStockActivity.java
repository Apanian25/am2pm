package com.dawson.jonat.stockers.StockQuote;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dawson.jonat.stockers.Menu.Menus;
import com.dawson.jonat.stockers.R;
import com.dawson.jonat.stockers.UserStock.RetrieveUserBalance;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class responsible for displaying information about a stock which is presented
 * by the ticker symbol the user inputs
 *
 * @author Lara Mezirovsky, Nicholas Apanian
 * @version 1.0.0
 */
public class ShowStockActivity extends Menus {

    TextView ticker, companyName, price, stockExcahnge, max, balance, error;
    ConnectivityManager connectionManager; //Class that answers queries about the state of network connectivity.
    NetworkInfo netInfo;
    String tickerText;
    LinearLayout buyLayout;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    int amount;
    double priceOfStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkNetwrok()) {
            setContentView(R.layout.activity_show_stock);
            //get all the elements needed
            ticker = findViewById(R.id.ticker);
            companyName = findViewById(R.id.companyName);
            price = findViewById(R.id.currentPrice);
            stockExcahnge = findViewById(R.id.stockExchange);
            tickerText = getIntent().getExtras().getString("ticker");
            buyLayout =  findViewById(R.id.buylayout);
            max = findViewById(R.id.maxAmount);
            balance = findViewById(R.id.currentBalance);
            error = findViewById(R.id.error);
            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        } else {
            //redirection to error page (503 error)
            setContentView(R.layout.error_page);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        setBalance();
        showStockQuotes();
    }

    /**
     * Start the thread that show info about the stock quotes
     */
    public void showStockQuotes() {
        //get the url
        String url = "https://www.worldtradingdata.com/api/v1/stock?symbol=" + tickerText + "&api_token=mUPqsrk2HXuiNHZqGkMvLicpZoLi1bXzTPXMbJIZj1BEMsnodb2lSmrsypwT";
        new StocksThread().execute(url);
    }


    /**
     * Retrieves the token.
     *
     * @return
     */
    private String getToken() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String token = sp.getString("token", null);
        return token;
    }

    /**
     * Calculates the maximum amount of stocks the user can afford to buy
     */
    public void calculateMax(){
        String balanceAmount = balance.getText().toString();
        int maximum = (int) Math.floor(Double.valueOf(balanceAmount.substring(balanceAmount.indexOf(" ") + 1)) / Double.valueOf(priceOfStock));

        if (maximum > Integer.valueOf(amount)) {
            maximum = Integer.valueOf(amount);
        }

        max.setText(getResources().getString(R.string.maximum) + maximum);
    }

    /**
     * Calls on the API to buy the stocks and updates the user the balance ann their maximum amount
     * of stocks they can buy
     * @param view
     */
    public void buyStock(View view) {
        //make a post req
        BuyStocks buy = new BuyStocks(this, ((EditText)findViewById(R.id.quantity)).getText().toString(),
                ((TextView)findViewById(R.id.ticker)).getText().toString());
        buy.execute(prefs.getString("token", "no token available"));
    }

    /**
     * Sets the users current balance
     */
    public void setBalance() {
        RetrieveUserBalance setBalance = new RetrieveUserBalance(this, getToken(), balance);
        setBalance.getCashAndDisplay();
    }

    /**
     * Inner class to represent the thread
     */
    private class StocksThread extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            try {
                return getStockData(strings[0]);
            } catch (IOException ioe) {
                return null;
            }
        }

        protected void onPostExecute(String[] result) {
            /**
             * Assume array stores the following values - structure: 0 - company
             * name 1 - price 2- currency 3 - stock exchange
             */
            try {
                ticker.setText(tickerText);
                companyName.setText(result[0]);
                price.setText(result[1] + " " + result[2]);
                stockExcahnge.setText(result[3]);
                String invalidToken = getResources().getString(R.string.wrong_credential);
                if(prefs.getString("token", invalidToken).equals(invalidToken)) {

                    showError(invalidToken);
                } else{
                    //set the max quantity
                    error.setVisibility(View.INVISIBLE);
                    setPriceAndQuantity(result[4], result[1]);
                    calculateMax();
                }
            } catch (NullPointerException np) {
                ticker.setText(getString(R.string.no_results_found) + "  " + tickerText);
                buyLayout.setVisibility(LinearLayout.GONE);
            }
        }

        /**
         * Private helper method to create a a connection with the API
         */
        private HttpURLConnection setTheConnection(HttpURLConnection httpURLConnection, URL url) throws IOException {
            //init
            httpURLConnection = (HttpURLConnection) url.openConnection();
            //set all the field thats will initiazlie the connection
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true); //allow the receive data
            httpURLConnection.connect();
            return httpURLConnection;
        }

        /**
         * Calls helper method to return the appropriate text from the api if
         * connection is OK
         *
         * @param urlText
         * @return
         * @throws MalformedURLException
         */
        private String[] getStockData(String urlText) throws IOException {
            //input stream variable
            InputStream input = null;
            //httpConnection variable
            HttpURLConnection httpUrlCon = null;
            //create a new URL Object
            URL url = new URL(urlText);

            //try to make a connection
            try {
                httpUrlCon = setTheConnection(httpUrlCon, url);
                //check the if the connection was succesful
                int response = httpUrlCon.getResponseCode();
                if (response != HttpURLConnection.HTTP_OK) {
                    //the response didnt succeed
                    return null;
                }
                //if still here, the response was fine
                //get the stream to the input
                input = httpUrlCon.getInputStream();
                String[] results = new String[5]; //assume - company name  --> 0, price --> 1 currency at --> 2, stock ex -->3
                try {
                    JSONObject json = new JSONObject(convertResponseToString(input));
                    JSONObject resultsToReturn = json.getJSONArray("data").getJSONObject(0);
                    results[0] = resultsToReturn.getString("name");
                    results[1] = resultsToReturn.getString("price");
                    results[2] = resultsToReturn.getString("currency");
                    results[3] = resultsToReturn.getString("stock_exchange_long") + " ( " + resultsToReturn.getString("stock_exchange_short") + " )";
                    results[4] = resultsToReturn.getString("shares");
                    return results;

                } catch (JSONException np) {
                    return null;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            httpUrlCon.disconnect();
            input.close();
            return null;
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

            return new String(byteArrayOutputStream.toString());

        }
    }

    /**
     * Helper method to check network connectivity
     */
    public boolean checkNetwrok() {
        //init a connectionManager object
        connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //We dont care if its is wifi or mobile, we want to user the available network for us
        netInfo = connectionManager.getActiveNetworkInfo();
        /**
         * About permission: uses-permission -> requests some permission
         * permission -> what allows
         */
        if (netInfo != null && netInfo.isConnected()) {
            return true;

        } else {
            showError(getResources().getString(R.string.no_internet));
            return false;
        }
    }

    /**
     * Display error on page
     */
    public void showError(String errorMessage) {
        error.setText(errorMessage);
    }

    void setPriceAndQuantity(String amount, String price) {
        this.amount = Integer.valueOf(amount);
        this.priceOfStock = Double.valueOf(price);
    }
}

