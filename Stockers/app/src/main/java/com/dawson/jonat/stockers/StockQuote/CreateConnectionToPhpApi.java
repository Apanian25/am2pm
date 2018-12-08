package com.dawson.jonat.stockers.StockQuote;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;

public class CreateConnectionToPhpApi extends AsyncTask<String,Void,String> {


    private final Context context;

    public CreateConnectionToPhpApi(Context context) {
        this.context = context;
    }

    public HttpURLConnection createConnection(String address) throws IOException {
        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        return conn;
    }
    @Override
    protected String doInBackground(String ... credentials) {

        try {
            //process token
            HttpURLConnection conn = createConnection("http://stockers-web-app.herokuapp.com/api/user/login");
            JSONObject json = new JSONObject();
            json.put("email", credentials[0]);
            json.put("password", credentials[1]);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(
                    json.toString());
            out.flush();
            out.close();
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = conn.getInputStream();
                String token = (new JSONObject(convertResponseToString(in))).getString("token");
                Log.i("Testing", "toooooooooooooken:" + token);
                return token;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String token) {
        super.onPostExecute(token);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        //parse the token

        editor.putString("token" , token);
        editor.commit();
        Log.i("Testing", "This is --->" + prefs.getString("token", null));
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
