package com.dawson.jonat.stockers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dawson.jonat.stockers.LoanCalculator.LoanCalculatorActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, LoanCalculatorActivity.class));
    }
}
