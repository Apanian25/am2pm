package com.dawson.jonat.stockers.APIUtil;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper method that deals with selling a stock through the API
 * Deals with making the HttpRequest by building a SimpleAPICaller
 * And calling the APIUserThread and handles the response
 *
 * @author Danny
 */
public class APISell implements OnCompleted{

    //Logging
    private final String TAG = "APISell";

    //UI
    private OnCompleted c;

    //API Info
    private String bearerToken;

    //Base url for API call
    private final String BASEURL = "http://stockers-web-app.herokuapp.com/api/";

    /**
     * Stores the bearer token and an OnCompleted listener
     * to deal with the response
     *
     * @param c
     * @param bearerToken
     */
    public APISell(OnCompleted c, String bearerToken){
        this.c = c;
        this.bearerToken = bearerToken;
    }

    /**
     * Validates quantity and makes the API call by building the Http Request
     * and pass it to the thread
     *
     * @param ticker
     * @param quantity
     * @throws IllegalArgumentException
     */
    public void sell(String ticker, int quantity) throws IllegalArgumentException{
        final String SELLROUTE = "api/sell";

        //Quantity validation
        if(quantity <= 0){
            throw new IllegalArgumentException("The quantity value needs to be positive");
        }

        //Builds the params
        Map<String, String> params = new HashMap<>();
        params.put("ticker", ticker);
        params.put("quantity", "" + quantity);

        SimpleAPICaller caller = new SimpleAPICaller(BASEURL + SELLROUTE, HttpMethods.POST, params, bearerToken);
//        SimpleAPICaller caller = new SimpleAPICaller("http://stockers-web-app.herokuapp.com/api/api/allstocks", HttpMethods.GET, null, bearerToken);
        APIUserThread thread = new APIUserThread(this);
        thread.execute(caller);
    }


    /**
     * Calls the OnCompleted listener instance
     *
     * @param response
     */
    @Override
    public void OnTaskCompleted(SimpleAPIResponse response) {
        Log.i(TAG, response.getMessageBody() + "*" + response.getStatusCode());
        c.OnTaskCompleted(response);
    }


}
