package com.dawson.jonat.stockers.Messaging;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dawson.jonat.stockers.Messaging.Articles.NewsInformation;
import com.dawson.jonat.stockers.Messaging.Articles.NewsRecyclerView;
import com.dawson.jonat.stockers.R;

import java.util.ArrayList;
import java.util.List;

public class NewsArticles extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        //Todo Query firebase in order to retrieve articles
        List<NewsInformation> articles = new ArrayList<>();

        articles.add(new NewsInformation("Article Title", "Short Descritpion about "
                + "the artcile.", "https://www.hltv.org/"));

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.newsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NewsRecyclerView(this, articles));
    }
}
