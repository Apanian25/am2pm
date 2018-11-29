package com.dawson.jonat.stockers.Hints;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.dawson.jonat.stockers.R;

public class FinancialHintsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_hints);

        ViewPager hintsViewPager = (ViewPager) findViewById(R.id.hintsViewPager);
    }
}
