package com.dawson.jonat.stockers.Messaging.ArticlesRecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dawson.jonat.stockers.R;

import java.util.List;

/**
 * Recycler view that is used to display the articles (in form of a List<NewsInformation>)
 */
public class NewsRecyclerView extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<NewsInformation> articles;
    private LayoutInflater inflater;

    /**
     * Initialize the Context that launced the recycler view, the list of newsInformation to be
     * displayed
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
        View view = inflater.inflate(R.layout.article_layout, viewGroup, false);
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

    /**
     * Return number of elements within the recycler view
     * @return
     */
    @Override
    public int getItemCount() {
        return articles.size();
    }
}
