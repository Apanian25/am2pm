package com.dawson.jonat.stockers.APIUtil;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class APISell implements OnCompleted{

    //Logging
    private final String TAG = "APISell";

    //UI
    private ProgressBar pb;
    private Context c;

    //API Info
    private String bearerToken;


    private final String BASEURL = "http://stockers-web-app.herokuapp.com/api/";

    public APISell(ProgressBar pb, Context c, String bearerToken){
        this.pb= pb;
        this.c = c;
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
        thread.execute(caller);
    }


    @Override
    public void OnTaskCompleted(SimpleAPIResponse response) {
        Log.i(TAG, response.getMessageBody() + "*" + response.getStatusCode());
    }

    private void parseAPISellResponse(SimpleAPIResponse response) throws JSONException {
        JSONObject obj = (JSONObject) new JSONTokener(response.getMessageBody()).nextValue();
        if(obj.has("cashleft")){
            Toast.makeText(c, "You have " + obj.getString("cashleft") + " of cash left after selling", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(c, "Error the attempt of selling", Toast.LENGTH_SHORT).show();
        }
    }


}
