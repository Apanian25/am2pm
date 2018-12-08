package com.dawson.jonat.stockers.APIUtil;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Seperates main thread concerns of API calls
 * Seperate thread used for any Http Request calls dealing with user's stocks
 * Abstracted by receiving a SimpleAPICaller
 *
 * @author Danny
 */
public class APIUserThread extends AsyncTask<SimpleAPICaller, Integer, SimpleAPIResponse>{

    private final String TAG = "APIThread";
    private static final int NETIOBUFFER = 1024;
    private OnCompleted listener;

    /**
     * Instantiates the OmCompleted handler
     *
     * @param listener
     */
    public APIUserThread(OnCompleted listener){
        this.listener = listener;
    }

    /**
     * Calls OnCompleted Listener when task is done and has an Api Response
     *
     * @param simpleAPIResponse
     */
    @Override
    protected void onPostExecute(SimpleAPIResponse simpleAPIResponse) {
        super.onPostExecute(simpleAPIResponse);
        listener.OnTaskCompleted(simpleAPIResponse);
    }

    /**
     *  Makes the API call and publishes progress
     *  First string args string[0] =>
     *
     * @param callers
     * @return
     */
    @Override
    protected SimpleAPIResponse doInBackground(SimpleAPICaller... callers) {
        //call progress method publishProgress()
        if(callers.length != 1){
            return null;
        }

        try{
            return callAPI(callers[0]);
        }catch (IOException e){
            Log.i(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Calls API according to HttpMethod type
     * And returns a SimpleAPIResponse
     *
     * @param caller
     * @return
     * @throws IOException
     */
    private SimpleAPIResponse callAPI(SimpleAPICaller caller) throws IOException{
        HttpURLConnection urlConn = null;

        //Calls API and waits for a response
        if(caller.getMethod() == HttpMethods.GET){
            urlConn = buildAPIGet(caller);
        }else if(caller.getMethod() == HttpMethods.POST){
            urlConn = buildAPIPost(caller);
        }


        if(urlConn != null){
            String response;
            int responseCode = urlConn.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                response = readStream(urlConn.getInputStream());
            }else{
                response = readStream(urlConn.getErrorStream());
            }

            return new SimpleAPIResponse(responseCode, response);
        }else{
            return null;
        }
    }

    /**
     * Used for reading an API response stream
     *
     * Taken from readIt() method from HttpExample.java in
     * gitlab: https://gitlab.com/Android518-2018/topic12-02-http-get
     *
     * @param is
     * @return
     * @throws IOException
     */
    private String readStream(InputStream is) throws IOException {
        int bytesRead, totalRead = 0;
        byte[] buffer = new byte[NETIOBUFFER];

        BufferedInputStream bufferedInputStream = new BufferedInputStream(is);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream writer = new DataOutputStream(byteArrayOutputStream);

        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
            writer.write(buffer);
            totalRead += bytesRead;
        }
        writer.flush();
        return new String(byteArrayOutputStream.toString());
    }


    /**
     * Builds a get APICall with the get specific settings
     *
     * @param caller
     * @return
     * @throws IOException
     */
    private HttpURLConnection buildAPIGet(SimpleAPICaller caller) throws IOException {
        URL url = new URL(caller.getUrlAPI() + caller.buildQueryString());
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        basicBuild(urlConn, caller);
        return urlConn;
    }

    /**
     * Builds a post APICall with the post specific settings
     *
     * @param caller
     * @return
     * @throws IOException
     */
    private HttpURLConnection buildAPIPost(SimpleAPICaller caller) throws IOException{
        URL url = new URL(caller.getUrlAPI());
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        basicBuild(urlConn, caller);
        writePostParams(urlConn, caller.buildPostParams());
        return urlConn;
    }

    /**
     * Adds all the common setting for any type of method calls on the HttpURLConnection
     *
     * @param urlConn
     * @param caller
     * @throws IOException
     */
    private void basicBuild(HttpURLConnection urlConn, SimpleAPICaller caller) throws IOException{
        urlConn.setReadTimeout(10000);
        urlConn.setConnectTimeout(15000);
        urlConn.setRequestMethod(caller.getMethod().toString().toUpperCase());
        urlConn.setDoInput(true);
        urlConn.setDoOutput(true);

        if(caller.getBearer() != null){
            urlConn.addRequestProperty("Authorization", "Bearer " + caller.getBearer());
        }
    }

    /**
     * Writes the string params to the HttpUrlConnection
     *
     * @param con
     * @param params
     * @throws IOException
     */
    private void writePostParams(HttpURLConnection con, String params) throws IOException{
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
        outputStreamWriter.write(params);
        outputStreamWriter.flush();
    }

}
