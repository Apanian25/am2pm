package com.dawson.jonat.stockers.APIUtil;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

/**
 * Does API calls to attempt in retrieving a bearer token
 * By authenticating
 *
 */
public class APIAuth implements OnCompleted{

    //Logging
    private final String TAG = "APIAuth";

    //Fields
    private String email;
    private String password;
    private String name;

    //Saving
    private SharedPreferences sp;

    //Base url for API call
    private final String BASEURL = "http://stockers-web-app.herokuapp.com/api/";

    private boolean firstAPIcall = true;

    /**
     * Instantiates credentials
     *
     * @param email
     * @param password
     * @param name
     * @param sp
     */
    public APIAuth(String email, String password, String name, SharedPreferences sp){
        this.email = email;
        this.password = password;
        this.name = name;
        this.sp = sp;
    }

    /**
     * Attempt to login according to the credentials given
     * If it fails, it will try to register
     * and then store the token in SharedPreferences
     *
     */
    public void authenticate(){
        login();
        firstAPIcall = true;
    }

    /**
     * Attempts to login by calling the login API
     */
    private void login(){
        final String LOGINROUTE = "user/login";


        Map<String, String> params = new HashMap<>();
        params.put("email", this.email);
        params.put("password", this.password);

        SimpleAPICaller caller = new SimpleAPICaller(BASEURL + LOGINROUTE, HttpMethods.POST, params, null);
        APIUserThread thread = new APIUserThread(this);
        thread.execute(caller);
    }

    /**
     * Attemps to register by calling the register API
     */
    private void register(){
        final String REGISTERROUTE = "user/register";


        Map<String, String> params = new HashMap<>();
        params.put("email", this.email);
        params.put("password", this.password);
        params.put("name", this.name);

        SimpleAPICaller caller = new SimpleAPICaller(BASEURL + REGISTERROUTE, HttpMethods.POST, params, null);
        APIUserThread thread = new APIUserThread(this);
        thread.execute(caller);
    }

    /**
     * Deals with the response and attempts to get a token
     *
     * @param response
     */
    @Override
    public void OnTaskCompleted(SimpleAPIResponse response) {
        JSONObject obj = parseResponse(response);

        try {
            if (obj != null && obj.has("token")) {
                saveToken(obj.getString("token"));
            }else{
                if(obj.has("error") && firstAPIcall){
                    register();
                    firstAPIcall = false;
                }
            }
        }catch(JSONException e){
            Log.i(TAG, "There was an unexpected error");
        }
    }

    /**
     * Saves token in SharedPreferences
     *
     * @param token
     */
    private void saveToken(String token){
        SharedPreferences.Editor  editor = this.sp.edit();
        editor.putString("token", token);
        editor.commit();
    }

    /**
     * Deals with exception throws returns appropriate json object
     *
     * @param response
     * @return
     */
    private JSONObject parseResponse(SimpleAPIResponse response){
        try{
            JSONObject obj = (JSONObject) new JSONTokener(response.getMessageBody()).nextValue();
            return obj;
        }catch(JSONException e){
            return null;
        }
    }


}
