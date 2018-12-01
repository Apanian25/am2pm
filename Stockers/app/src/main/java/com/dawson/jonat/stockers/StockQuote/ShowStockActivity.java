package com.dawson.jonat.stockers.StockQuote;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import java.net.MalformedURLException;
import java.net.URL;

public class ShowStockActivity extends Menus {
    TextView ticker,companyName, price, stockExcahnge;
    ConnectivityManager connectionManager; //Class that answers queries about the state of network connectivity.
    NetworkInfo netInfo;
    String tickerText;
    //Describes the status of a network interface.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(checkNetwrok()) {
            setContentView(R.layout.activity_show_stock);
            //get all the elements needed
            ticker = findViewById(R.id.ticker);
            companyName = findViewById(R.id.companyName);
            price = findViewById(R.id.currentPrice);
            stockExcahnge = findViewById(R.id.stockExchange);
            tickerText = getIntent().getExtras().getString("ticker");
            showStockQuotes();
        }
        else{
            setContentView(R.layout.error_page);
        }
    }

    public void showStockQuotes(){
        //get the url
        String url = "https://www.worldtradingdata.com/api/v1/stock?symbol="+tickerText+"&api_token=Qqn56QrK7FSkUbQxb3OFnZAqzKdAZ7NrMiGjPJgg2ky1qPywjPtETg81lbcB";
            new StocksThread().execute(url);
        
    }
    /**
     * Inner class to represent my thread
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
             * Assume array stores the following values - structure:
             * 0 - company name
             * 1 - price
             * 2- currency
             * 3 - stock exchange
             */
           try{
                ticker.setText(tickerText);
                companyName.setText(result[0]);
                price.setText(result[1] + " " +  result[2]);
                stockExcahnge.setText(result[3]);
            }
            catch (NullPointerException np){
                ticker.setText(getString(R.string.no_results_found) + "  " + tickerText);
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
         * Calls helper method to return the appropriate text from the api if connection is OK
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
                String[] results = new String[4]; //assume - company name  --> 0, price --> 1 currency at --> 2, stock ex -->3
                try {
                    JSONObject json = new JSONObject(convertResponseToString(input));
                    JSONObject resultsToReturn = json.getJSONArray("data").getJSONObject(0);
                    results[0] = resultsToReturn.getString("name");
                    results[1] = resultsToReturn.getString("price");
                    results[2] = resultsToReturn.getString("currency");
                    results[3] = resultsToReturn.getString("stock_exchange_long") + " ( " + resultsToReturn.getString("stock_exchange_short") + " )";
                    return results;

                }
                catch (JSONException np){
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
         * About permission:
         * uses-permission -> requests some permission
         * permission -> what allows
         */
        if (netInfo != null /**has been instantiated**/ && netInfo.isConnected()) {
            Log.i("Tag", "This is our net info--->    " + netInfo);
            return true;


        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
