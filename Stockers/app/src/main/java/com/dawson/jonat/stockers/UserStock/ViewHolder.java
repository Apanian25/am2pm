package com.dawson.jonat.stockers.UserStock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dawson.jonat.stockers.APIUtil.APIAuth;
import com.dawson.jonat.stockers.APIUtil.APISell;
import com.dawson.jonat.stockers.APIUtil.OnCompleted;
import com.dawson.jonat.stockers.APIUtil.OnTokenCompleted;
import com.dawson.jonat.stockers.APIUtil.SimpleAPIResponse;
import com.dawson.jonat.stockers.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, OnTokenCompleted {
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
        contextOfView = context;
        textViewTickerSymbol = (TextView) view.findViewById(R.id.tickerSymbol);
        textViewQuantity = (TextView) view.findViewById(R.id.quantity);
        textViewPurchasePrice = (TextView) view.findViewById(R.id.purchasePrice);
        view.setOnLongClickListener(this);
    }

    /**
     * Validates token before calling the sell api
     *
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contextOfView);
        String token = prefs.getString("token", null);

        APIAuth auth = new APIAuth(prefs.getString("email", ""), prefs.getString("password", ""), prefs.getString("fname", "") + " " + prefs.getString("lname", ""), prefs, this);
        auth.validateToken(token);
        return true;
    }

    /**
     * Decides action upon the response of a valid token
     * if false, it will try to authenticate
     * if true, it will sell
     *
     * @param validToken
     */
    @Override
    public void onTaskCompleted(boolean validToken) {
        if(validToken){
            sell();
        }else{
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contextOfView);
            APIAuth auth = new APIAuth(prefs.getString("email", ""), prefs.getString("password", ""), prefs.getString("fname", "") + " " + prefs.getString("lname", ""), prefs, this);
            auth.authenticate();
            Toast.makeText(contextOfView, "Authentication failed please try again", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Calls the sell API
     */
    public void sell(){
        if (contextOfView instanceof OnCompleted) {

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(contextOfView);
            String token = sp.getString("token", null);



            if(token == null){
                Toast.makeText(contextOfView, "You are not authenticated please look at your settings", Toast.LENGTH_SHORT);
            }else {

                APISell apisell = new APISell((OnCompleted) contextOfView, token);


                String ticker = textViewTickerSymbol.getText().toString();
                String quantity = textViewQuantity.getText().toString();
                quantity = quantity.split(" ")[1];


                try {
                    apisell.sell(ticker, Integer.parseInt(quantity));
                } catch (IllegalArgumentException e) {
                    Toast.makeText(contextOfView, e.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        }
    }
}
