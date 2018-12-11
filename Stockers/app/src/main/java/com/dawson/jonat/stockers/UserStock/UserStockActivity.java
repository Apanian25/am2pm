package com.dawson.jonat.stockers.UserStock;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dawson.jonat.stockers.APIUtil.APIAuth;
import com.dawson.jonat.stockers.APIUtil.APISell;
import com.dawson.jonat.stockers.APIUtil.APIUserThread;
import com.dawson.jonat.stockers.APIUtil.HttpMethods;
import com.dawson.jonat.stockers.APIUtil.OnCompleted;
import com.dawson.jonat.stockers.APIUtil.OnTokenCompleted;
import com.dawson.jonat.stockers.APIUtil.SimpleAPICaller;
import com.dawson.jonat.stockers.APIUtil.SimpleAPIResponse;
import com.dawson.jonat.stockers.Menu.Menus;
import com.dawson.jonat.stockers.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserStockActivity extends Menus implements OnCompleted, OnTokenCompleted{

    /**
     * On create uses the RetrieveUserBalance and RetrieveUserStock in order to get and display
     * the users balance and all of their stocks that they currently own.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stock);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String user = prefs.getString("fname", "") + " " + prefs.getString("lname", "");


        ((TextView) findViewById(R.id.UserName)).setText(user);
        APIAuth auth = new APIAuth(prefs.getString("email", ""), prefs.getString("password", ""), prefs.getString("fname", "") + " " + prefs.getString("lname", ""), prefs, this);
        auth.validateToken(getBearerToken());

    }
    private void retrieve(){
        RetrieveUserBalance retrieveUserBalance = new RetrieveUserBalance(this, getBearerToken(),
                (TextView)findViewById(R.id.balance));
        retrieveUserBalance.getCashAndDisplay();

        RetrieveUserStock retrieveUserStock = new RetrieveUserStock(this, getBearerToken(),
                (RecyclerView) findViewById(R.id.displayStocks));
        retrieveUserStock.getStocksAndDisplay();
    }



    /**
     * Retrieves the bearer token.
     *
     * @return
     */
    private String getBearerToken() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String token = sp.getString("token", null);
        return token;
    }

    @Override
    public void OnTaskCompleted(SimpleAPIResponse response) {
        //Refresh the page
        //Temporary if you have another solution
        refresh();
        Toast.makeText(this, "Your has been sold", Toast.LENGTH_SHORT).show();
    }

    private void refresh(){
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onTaskTokenCompleted(boolean validToken) {
        if(validToken){
            retrieve();
        }else{
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            APIAuth auth = new APIAuth(prefs.getString("email", ""), prefs.getString("password", ""), prefs.getString("fname", "") + " " + prefs.getString("lname", ""), prefs, this);
            auth.authenticate();
            refresh();
        }
    }
}
