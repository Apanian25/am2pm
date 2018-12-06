package com.dawson.jonat.stockers.UserStock;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.dawson.jonat.stockers.Messaging.ArticlesRecyclerView.NewsRecyclerView;
import com.dawson.jonat.stockers.R;

import java.util.ArrayList;
import java.util.List;

public class UserStockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stock);

        String balance = getBalance();
        List<StockInformation> stocks = getStocks();
        String user = getIntent().getExtras().getString("username");

        ((TextView)findViewById(R.id.UserName)).setText(user);
        ((TextView)findViewById(R.id.balance)).setText(balance);

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
}
