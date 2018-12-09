package com.dawson.jonat.stockers.Hints;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dawson.jonat.stockers.Entity.Hint;
import com.dawson.jonat.stockers.Menu.Menus;
import com.dawson.jonat.stockers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base activity for the view pager and the hint fragments
 *
 * @author Danny
 */
public class FinancialHintsActivity extends Menus {

    //Logging tag
    private final String TAG = "FinancialHintsActivity";

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    //Views in layout
    private ViewPager hintsViewPager;
    private ProgressBar progressBar;

    private List<Hint> hintList;

    /**
     * Loads the hints view pager
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (hasInternet()) {
            setContentView(R.layout.activity_financial_hints);

            //Display loading bar due to delay in getting the hints
            RelativeLayout hintsLayout = findViewById(R.id.hints_layout);
            progressBar = new ProgressBar(FinancialHintsActivity.this, null, android.R.attr.progressBarStyleHorizontal);
            hintsLayout.addView(progressBar, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);


            //Fetch hints list and displays it
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.i(TAG, "Anonymous SignIn has been successful");
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                hintList = new ArrayList<>();
                                for (DataSnapshot child : dataSnapshot.child("hints").getChildren()) {
                                    hintList.add(child.getValue(Hint.class));
                                }
                                Collections.shuffle(hintList);


                                hintsViewPager = (ViewPager) findViewById(R.id.hintsViewPager);
                                HintsFragmentPagerAdapter pagerAdapter = new HintsFragmentPagerAdapter(getSupportFragmentManager(), hintList);
                                progressBar.setVisibility(View.GONE);
                                hintsViewPager.setAdapter(pagerAdapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Log.i(TAG, "Anonymous SignIn has been fail");
                    }
                }
            });
        }else{
            setContentView(R.layout.error_page);
        }
    }


    public boolean hasInternet() {
        ConnectivityManager connectionManager; //Class that answers queries about the state of network connectivity.
        NetworkInfo netInfo;
        connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //We dont care if its is wifi or mobile, we want to user the available network for us
        netInfo = connectionManager.getActiveNetworkInfo();
        /**
         * About permission: uses-permission -> requests some permission
         * permission -> what allows
         */
        if (netInfo != null && netInfo.isConnected()) {
            return true;

        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
