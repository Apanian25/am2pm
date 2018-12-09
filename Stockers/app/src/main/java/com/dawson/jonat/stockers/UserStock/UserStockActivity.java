package com.dawson.jonat.stockers.UserStock;

import android.os.Bundle;
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

public class UserStockActivity extends Menus {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stock);

        String user = getIntent().getExtras().getString("username");

        ((TextView) findViewById(R.id.UserName)).setText(user);

        RetrieveUserBalance retrieveUserBalance = new RetrieveUserBalance(this, getBearerToken(),
                (TextView)findViewById(R.id.balance));
        retrieveUserBalance.getCashAndDisplay();

        RetrieveUserStock retrieveUserStock = new RetrieveUserStock(this, getBearerToken(),
                (RecyclerView) findViewById(R.id.displayStocks));
        retrieveUserStock.getStocksAndDisplay();
    }

    private String getBearerToken() {
        return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsImlzcyI6Imh0dHA6Ly9zdG9ja2Vycy13ZWItYXBwLmhlcm9rdWFwcC5jb20vYXBpL3VzZXIvbG9naW4iLCJpYXQiOjE1NDQzMTUxMTIsImV4cCI6MTU0NDMxODcxMiwibmJmIjoxNTQ0MzE1MTEyLCJqdGkiOiJtTk9LN1FuWkJKdEVwNXJZIn0.qUxCX0vhm_FpBMnnLQyYvboLRBF56cK7mwZUA-IZVNU";

    }


}
