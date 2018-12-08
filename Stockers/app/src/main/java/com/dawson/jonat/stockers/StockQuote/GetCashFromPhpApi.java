package com.dawson.jonat.stockers.StockQuote;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetCashFromPhpApi extends AsyncTask<String,Void,Integer> {


    private final Context context;
    private HttpURLConnection conn;

    public GetCashFromPhpApi (Context context) {
        this.context = context;
    }

    public void createConnection(String address) throws IOException {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String token = prefs.getString("token", "");
        URL url = new URL(address);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + token);
    }
    @Override
    protected Integer doInBackground(String ... information) {

        try {
            //process token
            createConnection("http://stockers-web-app.herokuapp.com/api/api/cash");
            Log.i("Testing", "" + conn.getResponseCode());
            Log.i("Testing", "" + conn.getResponseMessage());
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i("Testing", "I am here");
                InputStream in = conn.getInputStream();
                String balance = (new JSONObject(convertResponseToString(in))).getString("cash");
                Log.e("Testing", "toooooooooooooken:" + balance);
                //calculate max balance/price
                int max = (int) Math.floor(Double.valueOf(balance) / Double.valueOf(information[2]));
                Log.e("Testing", "This is balance--> " + Double.valueOf(balance));
                Log.e("Testing", "This is info--> " + Double.valueOf(information[2]));
                Log.e("Testing", "This is max--> " + max);
                //if the max amount is bigger than the available stock then ---> max = available stock
                if (max > Integer.valueOf(information[1])) {
                    max = Integer.valueOf(information[1]);
                }
                return max;
            }
        } catch (IOException | JSONException e) {
            Log.i("Testing", "In catch statement");
            Log.i("Testing", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Integer max) {
        super.onPostExecute(max);
        //store max in shared prefs
        //parse the token
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Log.i("Testing", "value of max: " + max);
        editor.putInt("max" , max);
        editor.commit();
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
