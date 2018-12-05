package com.dawson.jonat.stockers;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import com.dawson.jonat.stockers.ContactDialog.ContactInformation;
import com.dawson.jonat.stockers.CurrecnyExchange.CurrencyExchangeActivity;
import android.preference.PreferenceManager;
import com.dawson.jonat.stockers.LoanCalculator.LoanCalculatorActivity;
import android.view.View;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.dawson.jonat.stockers.Menu.AboutActivity;
import com.dawson.jonat.stockers.Menu.SettingsActivity;
import com.dawson.jonat.stockers.Messaging.NewsArticlesActivity;
import com.dawson.jonat.stockers.Messaging.SubscriptionManager;
import com.dawson.jonat.stockers.Notes.NoteActivity;
import com.dawson.jonat.stockers.Hints.FinancialHintsActivity;
import com.dawson.jonat.stockers.StockQuote.StockQuotesActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import com.dawson.jonat.stockers.Menu.Menus;

public class MainActivity extends Menus {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        //Check if first visit


        TextView userTV = findViewById(R.id.usernameLbl);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        userTV.setText(sp.getString("fname" , "") + " " + sp.getString("lname", null));

        //Instantiate firebase auth
        mAuth = FirebaseAuth.getInstance();

        //Sign the user in with the predefined authentication identification
        mAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(context, "This Worked", Toast.LENGTH_LONG).show();
            }
        });

        mAuth.signInAnonymously();
        //Subscribe to news service
        SubscriptionManager.sub("News", this, false);


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String email = sp.getString("email", null);
        if(email == null){
            Intent intent = new Intent(this, SettingsActivity.class);
            launchActivity(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //To set after setting has been saved
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        TextView userTV = findViewById(R.id.usernameLbl);
        userTV.setText(sp.getString("fname" , "") + " " + sp.getString("lname", null));
    }

    //Start activities when the corresponding button is clicked

    public void foreignExchangeClick(View v) {

    }

    public void financialHintsClick(View v) {
        startActivity(new Intent(getApplicationContext(), FinancialHintsActivity.class));
    }

    public void stockQuoteClick(View v) {
        startActivity(new Intent(MainActivity.this, StockQuotesActivity.class));
    }

    public void notesClick(View v) {
        startActivity(new Intent(MainActivity.this, NoteActivity.class));
    }

    public void loanCalculatorClick(View v) {
        startActivity(new Intent(this, LoanCalculatorActivity.class));
    }

    public void portfolioClick(View v) {
            //todo
    }

    public void messageClick(View v){
        startActivity(new Intent(this, NewsArticlesActivity.class));
    }

    public void onClickAboutPage(View view) {
        startActivity(new Intent(this, AboutActivity.class));
    }
}
