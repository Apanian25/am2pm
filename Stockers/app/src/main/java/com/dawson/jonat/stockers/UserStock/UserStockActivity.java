package com.dawson.jonat.stockers.UserStock;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dawson.jonat.stockers.APIUtil.APISell;
import com.dawson.jonat.stockers.APIUtil.OnCompleted;
import com.dawson.jonat.stockers.APIUtil.SimpleAPIResponse;
import com.dawson.jonat.stockers.Menu.Menus;
import com.dawson.jonat.stockers.R;

import java.util.ArrayList;
import java.util.List;

public class UserStockActivity extends Menus implements OnCompleted{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stock);

        String balance = getBalance();
        List<StockInformation> stocks = getStocks();
        String user = getIntent().getExtras().getString("username");

        ((TextView)findViewById(R.id.UserName)).setText(user);
        ((TextView)findViewById(R.id.balance)).setText(getResources().getString(R.string.balance) + " " + balance);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.displayStocks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserStockRecyclerView(this, stocks));
    }

    private String getBalance() {
        return "20.00";
    }

    private List<StockInformation> getStocks() {

        List<StockInformation> stocks = new ArrayList();
        stocks.add(new StockInformation("ABC", "30", "2132.23"));
        stocks.add(new StockInformation("GOOGL", "62", "2356.24"));
        stocks.add(new StockInformation("ABC", "30", "2132.23"));
        stocks.add(new StockInformation("GOOGL", "62", "2356.24"));

        return stocks;
    }

    @Override
    public void OnTaskCompleted(SimpleAPIResponse response) {
        //Refresh the page
        finish();
        startActivity(getIntent());
        Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show();
    }
}
