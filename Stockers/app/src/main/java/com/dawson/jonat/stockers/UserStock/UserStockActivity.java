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

import com.dawson.jonat.stockers.APIUtil.APISell;
import com.dawson.jonat.stockers.APIUtil.APIUserThread;
import com.dawson.jonat.stockers.APIUtil.HttpMethods;
import com.dawson.jonat.stockers.APIUtil.OnCompleted;
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

public class UserStockActivity extends Menus implements OnCompleted{

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
        finish();
        startActivity(getIntent());
        Toast.makeText(this, "Your has been sold", Toast.LENGTH_SHORT).show();
    }
}
