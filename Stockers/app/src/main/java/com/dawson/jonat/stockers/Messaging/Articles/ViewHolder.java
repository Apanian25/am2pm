package com.dawson.jonat.stockers.Messaging.Articles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.dawson.jonat.stockers.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String SUBJECT = "Credit Loan Calculation Results";
    private Context contextOfView;

    TextView textViewTitle;
    TextView textViewDescription;
    String url;

    /**
     * Users
     *
     * @param view
     * @param context
     */
    public ViewHolder(View view, Context context) {
        super(view);
        textViewTitle = (TextView)view.findViewById(R.id.title);
        textViewDescription = (TextView)view.findViewById(R.id.shortDescription);
        view.setOnClickListener(this);
    }

    /**
     * When an view holder is clicked, it will launch an intent to send an email
     * @param v
     */
    @Override
    public void onClick(View v) {
        //Here the article link should be sent
    }
}
