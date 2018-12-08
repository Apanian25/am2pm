package com.dawson.jonat.stockers.APIUtil;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class APISell implements OnCompleted{

    //Logging
    private final String TAG = "APISell";

    //UI
    private ProgressBar pb;
    private View v;

    //API Info
    private String bearerToken;


    private final String BASEURL = "http://ass3.test/api/";

    public APISell(ProgressBar pb, View v, String bearerToken){
        this.pb= pb;
        this.v = v;
        this.bearerToken = bearerToken;
    }

    public void sell(String ticker, int quantity) throws IllegalArgumentException{
        final String SELLROUTE = "api/sell";

        if(quantity <= 0){
            throw new IllegalArgumentException("The quantity value needs to be positive");
        }

        Map<String, String> params = new HashMap<>();
        params.put("ticker", ticker);
        params.put("quantity", "" + quantity);

        SimpleAPICaller caller = new SimpleAPICaller(BASEURL + SELLROUTE, HttpMethods.POST, params, bearerToken);
        APIUserThread thread = new APIUserThread(this.pb, this);
        thread.execute();
    }


    @Override
    public void OnTaskCompleted(SimpleAPIResponse response) {
        Log.i(TAG, response.getMessageBody() + "*" + response.getStatusCode());
    }


}
