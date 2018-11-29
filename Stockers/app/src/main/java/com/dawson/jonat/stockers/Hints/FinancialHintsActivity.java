package com.dawson.jonat.stockers.Hints;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

public class FinancialHintsActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ViewPager hintsViewPager;
    private final String TAG = "FinancialHintsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_hints);


        mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i(TAG, "Anonymous SignIn has been successful");
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<Hint> hintList = new ArrayList<>();
                            for(DataSnapshot child :  dataSnapshot.child("hints").getChildren()){
                                hintList.add(child.getValue(Hint.class));
                            }
                            Collections.shuffle(hintList);



                            hintsViewPager = (ViewPager) findViewById(R.id.hintsViewPager);
                            HintsFragmentPagerAdapter pagerAdapter = new HintsFragmentPagerAdapter(getSupportFragmentManager(), hintList);
                            hintsViewPager.setAdapter(pagerAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else{
                    Log.i(TAG, "Anonymous SignIn has been fail");
                }
            }
        });
    }

}
