package com.dawson.jonat.stockers.Messaging.Articles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
     * Constructor that initializes the view holder
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
     * When an view holder is clicked, it will launch an to the url of the activity
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent urlIntent = new Intent(Intent.ACTION_VIEW);
        urlIntent.setData(Uri.parse(url));
        contextOfView.startActivity(urlIntent);
    }
}
