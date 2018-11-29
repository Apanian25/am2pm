package com.dawson.jonat.stockers.Hints;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

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
import java.util.List;
import java.util.Random;

public class HintsFragmentPagerAdapter extends FragmentPagerAdapter{
    private final String TAG = "HintsPagerAdapter";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private List<Hints> hintsList;

    public HintsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        hintsList = new ArrayList<>();
        //Firebase authentication
        //Firebase fetch object of hints
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
                            for(DataSnapshot child :  dataSnapshot.getChildren()){
                                hintsList.add(child.getValue(Hints.class));
                            }
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

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
//        switch(position) {
//            case 0: frag = new MondayFragment();
//                break;
//            case 1: frag =  new TuesdayFragment();
//                break;
//            case 2: frag = new WednesdayFragment();
//                break;
//
//        }
        if (frag == null) {
            throw new IllegalArgumentException("No matching fragment");
        }
        return frag;
    }

    @Override
    public int getCount() {
        return hintsList.size();
    }
}
