package com.dawson.jonat.stockers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;


import com.dawson.jonat.stockers.Hints.FinancialHintsActivity;
import com.dawson.jonat.stockers.Hints.HintsFragmentPagerAdapter;
import com.dawson.jonat.stockers.StockQuote.StockQuotesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import com.dawson.jonat.stockers.Menu.Menus;

public class MainActivity extends Menus {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void foreignExchangeClick(View v){
    }

    public void financialHintsClick(View v){
        startActivity(new Intent(getApplicationContext(), FinancialHintsActivity.class));
    }

    public void stockQuoteClick(View v){
        Intent intent = new Intent(MainActivity.this,StockQuotesActivity.class);
        startActivity(intent);
    }

    public void notesClick(View v){
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        startActivity(intent);
    }

    public void loanCalculatorClick(View v){

    }

    public void portfolioClick(View v){

    }

    public void messageClick(View v){

    }
}
