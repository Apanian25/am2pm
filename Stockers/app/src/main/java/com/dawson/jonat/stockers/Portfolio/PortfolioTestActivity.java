package com.dawson.jonat.stockers.Portfolio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.dawson.jonat.stockers.APIUtil.APISell;
import com.dawson.jonat.stockers.R;

public class PortfolioTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_test);

        APISell sell = new APISell(new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal), this, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjgsImlzcyI6Imh0dHA6Ly9zdG9ja2Vycy13ZWItYXBwLmhlcm9rdWFwcC5jb20vYXBpL3VzZXIvbG9naW4iLCJpYXQiOjE1NDQyOTEwMTQsImV4cCI6MTU0NDI5NDYxNCwibmJmIjoxNTQ0MjkxMDE0LCJqdGkiOiJkTU9renVIejNKNzZUYmp0In0.nAGlGm90jZ7-4aaOQ91fKe9Z2wXe1akJ45fH_9GIx4Y");
        sell.sell("ACB", 100);
    }
}
