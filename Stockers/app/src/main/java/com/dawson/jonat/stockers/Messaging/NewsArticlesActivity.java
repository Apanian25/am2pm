package com.dawson.jonat.stockers.Messaging;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dawson.jonat.stockers.Menu.Menus;
import com.dawson.jonat.stockers.Messaging.ArticlesRecyclerView.NewsInformation;
import com.dawson.jonat.stockers.Messaging.ArticlesRecyclerView.NewsRecyclerView;
import com.dawson.jonat.stockers.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Activity that is used to display all the articles that are contained with the firebase database.
 */
public class NewsArticlesActivity extends Menus {

    private DatabaseReference databaseReference;
    private Context context;

    /**
     * Initializes the context of the class, being the class itself, and calls on the method to fill
     * and display the recycler view for the articles.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        context = this;
        this.pullArticlesFromFirebaseAndAddToRecyclerView();
    }

    /**
     * Initializes the recycler view and will initialize it with the given list of article
     * information.
     *
     * @param articles
     */
    private void setUpRecyclerView(List<NewsInformation> articles) {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.newsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NewsRecyclerView(this, articles));
    }

    /**
     * Retrieve the articles from the database, and when it has retrieved all of the articles it will
     * use the articles to display them in the recycler view.
     */
    private void pullArticlesFromFirebaseAndAddToRecyclerView() {
        //Get a reference to the new Articles
        DatabaseReference articles = FirebaseDatabase.getInstance()
                .getReference().child("newArticles");

        //Get the values from the firebase database
        articles.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<NewsInformation> articles = new ArrayList<>();

                //Loop through the database children in order to get their values
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String title = snapshot.child("title").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String url = snapshot.child("url").getValue(String.class);

                    articles.add(new NewsInformation(title, description, url));
                }

                //Reverse the list so that the latest article goes will be first
                Collections.reverse(articles);

                //Now we can set up the recycler view
                setUpRecyclerView(articles);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
