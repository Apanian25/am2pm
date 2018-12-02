package com.dawson.jonat.stockers.Messaging.Articles;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawson.jonat.stockers.LoanCalculator.LoanPayoutSummary;
import com.dawson.jonat.stockers.R;

import java.util.List;

public class NewsRecyclerView extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<NewsInformation> articles;
    private LayoutInflater inflater;

    /**
     * Initialize the Context that launced the recycler view, the contacts to be displayed and the
     * LoanPayoutSummary to be sent in the email
     *
     * @param context
     */
    public  NewsRecyclerView(Context context, List<NewsInformation> articles) {
        this.inflater = LayoutInflater.from(context);
        this.articles = articles;
        this.context = context;
    }

    /**
     * Inflates the view and creates a view holder with it
     *
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.activity_article, viewGroup, false);
        return new ViewHolder(view, context);
    }

    /**
     * Binds the view holder with the contact data from that position
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.textViewTitle.setText(articles.get(position).getTitle());
        viewHolder.textViewDescription.setText(articles.get(position).getDescription());
        viewHolder.url = articles.get(position).getUrlToArticle();
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
