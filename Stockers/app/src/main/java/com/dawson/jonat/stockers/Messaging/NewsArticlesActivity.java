package com.dawson.jonat.stockers.Messaging;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dawson.jonat.stockers.Messaging.ArticlesRecyclerView.NewsInformation;
import com.dawson.jonat.stockers.Messaging.ArticlesRecyclerView.NewsRecyclerView;
import com.dawson.jonat.stockers.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewsArticlesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        //Todo Query firebase in order to retrieve articles
    }


}
