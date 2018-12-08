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

        APISell sell = new APISell(new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal), findViewById(R.id.tempo), "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIsImlzcyI6Imh0dHA6Ly9hc3MzLnRlc3QvYXBpL3VzZXIvbG9naW4iLCJpYXQiOjE1NDQyODgwMjAsImV4cCI6MTU0NDI5MTYyMCwibmJmIjoxNTQ0Mjg4MDIwLCJqdGkiOiI4WWxadEZTaU54a011U2xKIn0.wT4DyuX0T_5nVHtbOpvyabQ5Dd-izjCE3mJFg9wIWVw");
        sell.sell("ACB", 100);
    }
}
