package com.dawson.jonat.stockers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.dawson.jonat.stockers.Notes.NoteActivity;
import com.dawson.jonat.stockers.Hints.FinancialHintsActivity;
import com.dawson.jonat.stockers.Hints.HintsFragmentPagerAdapter;
import com.dawson.jonat.stockers.Hints.Hints;
import com.dawson.jonat.stockers.Messaging.Messaging;
import com.dawson.jonat.stockers.Messaging.NewsArticles;
import com.dawson.jonat.stockers.Messaging.NotificationUtilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.d("newToken",newToken);
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("News")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.subSuccess);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.subFailed);
                        }
                        Log.d("SUB", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
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
