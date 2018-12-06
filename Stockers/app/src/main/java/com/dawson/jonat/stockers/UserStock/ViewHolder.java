package com.dawson.jonat.stockers.UserStock;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.dawson.jonat.stockers.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    private Context contextOfView;

    TextView textViewTickerSymbol;
    TextView textViewQuantity;
    TextView textViewPurchasePrice;

    /**
     * Constructor that initializes the view holder setting the values of the three views
     *
     * @param view
     * @param context
     */
    public ViewHolder(View view, Context context) {
        super(view);
        textViewTickerSymbol = (TextView)view.findViewById(R.id.tickerSymbol);
        textViewQuantity = (TextView)view.findViewById(R.id.quantity);
        textViewPurchasePrice = (TextView)view.findViewById(R.id.purchasePrice);
    }
}
