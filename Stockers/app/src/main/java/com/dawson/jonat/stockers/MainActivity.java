package com.dawson.jonat.stockers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import com.dawson.jonat.stockers.Hints.FinancialHintsActivity;
import com.dawson.jonat.stockers.Hints.HintsFragmentPagerAdapter;
import android.widget.Toast;
import com.dawson.jonat.stockers.Hints.Hints;
import com.dawson.jonat.stockers.Messaging.Messaging;
import com.dawson.jonat.stockers.Messaging.NewsArticles;
import android.widget.Toast;
import com.dawson.jonat.stockers.Messaging.ArticlesRecyclerView.NewsRecyclerView;
import com.dawson.jonat.stockers.Messaging.NewsArticlesActivity;
import com.dawson.jonat.stockers.Messaging.NotificationUtilities;
import com.dawson.jonat.stockers.Messaging.SubscriptionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class MainActivity extends Activity {


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

        //This needs to be called everytime
        SubscriptionManager.sub("News", this, false);
    }

    public void goToNoteActivity(View view) {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        startActivity(intent);
    }
    public void foreignExchangeClick(View v){

    }

    public void financialHintsClick(View v){
        startActivity(new Intent(getApplicationContext(), FinancialHintsActivity.class));
    }

    public void stockQuoteClick(View v){

    }

    public void notesClick(View v){

    }

    public void loanCalculatorClick(View v){

    }

    public void portfolioClick(View v){

    }

    public void messageClick(View v){

    }
}
