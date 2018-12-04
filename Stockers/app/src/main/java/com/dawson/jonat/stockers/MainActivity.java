package com.dawson.jonat.stockers;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import com.dawson.jonat.stockers.ContactDialog.ContactInformation;
import com.dawson.jonat.stockers.LoanCalculator.LoanCalculatorActivity;
import java.io.InputStream;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.widget.Toast;

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



        //Instantiate firebase auth
        mAuth = FirebaseAuth.getInstance();

        //Sign the user in with the predefined authentication identification
        mAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(context, "This Worked", Toast.LENGTH_LONG).show();
            }
        });

        SubscriptionManager.sub("News", this, false);

    }

    public void foreignExchangeClick(View v) {
    }

    public void financialHintsClick(View v) {
        startActivity(new Intent(getApplicationContext(), FinancialHintsActivity.class));
    }

    public void stockQuoteClick(View v) {
        // what would usally be clicked
        Intent intent = new Intent(MainActivity.this, StockQuotesActivity.class);
        startActivity(intent);
    }

    public void notesClick(View v) {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        startActivity(intent);
    }

    public void loanCalculatorClick(View v) {
        Intent intent = new Intent(this, LoanCalculatorActivity.class);
        startActivity(intent);
    }

    public void portfolioClick(View v) {

    }

    public void messageClick(View v){
        Intent intent = new Intent(this, NewsArticlesActivity.class);
        startActivity(intent);
    }
}
