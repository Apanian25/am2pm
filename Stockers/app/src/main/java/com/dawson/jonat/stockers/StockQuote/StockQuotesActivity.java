package com.dawson.jonat.stockers.StockQuote;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.Toast;

import com.dawson.jonat.stockers.Entity.Ticker;
import com.dawson.jonat.stockers.Menu.Menus;
import com.dawson.jonat.stockers.R;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class responsible for displaying for creating a recycler View that
 * gathers up to 5 ticker symbols that the user can view
 *
 * @author Lara Mezirovsky
 * @version 1.0
 */
public class StockQuotesActivity extends Menus implements Serializable {
    ArrayList<Ticker> list;
    Set<String> tickers;
    EditText et;
    RecyclerView rv;
    private TickerAdapter tickerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_quotes);
        list = new ArrayList<>();
        //find edit text
        et = findViewById(R.id.tickerInput);
        rv = findViewById(R.id.tickerList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        tickers = new LinkedHashSet<String>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        tickers = prefs.getStringSet("listTickers", tickers);
        createTheAdapter();

    }
    /**
     * private helper method that generates an array  of tickers
     * Max length: 5 tickers symbols inputed by the user
     * Cant input "" or spac
     */
    private void createTheAdapter() {

//todo: if tickers not null load it, else have an empty data set
        if (tickers == null) {
            //we are going to have an empty array list
            list = new ArrayList<>(); //empty
        } else {
            for (String ticker : tickers) {
                //create a Ticker symbol
                list.add(new Ticker(ticker));
            }
        }

        tickerAdapter = new TickerAdapter(this, list);
        rv.setAdapter(tickerAdapter);
    }

    /**
     * Adds a Item to the list and notifies the Adapter that the list has changed
     * @param view
     */
    public void addTicker(View view) {
        if (list.size() < 5) {
            String text = et.getText().toString();
            //check if not empty string
            if((!(text.equals("") || text.isEmpty()))){
                list.add(new Ticker(text));
                //add this to the shared preferences
                tickerAdapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(this, R.string.empty_string, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.more_than_five, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Override onPause so that it saves the list of tickers
     * in the shared preferences
     */
    @Override
    public void onPause() {
        super.onPause();
        tickers.clear();
        for(Ticker ticker : list) {
            tickers.add(ticker.getSymbol());
        }
        saveListInShared();
    }

    /**
     * Helper method to saved the current list in shared preferences
     */
    public void saveListInShared(){
        //save the serializable list in shared preferences
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putStringSet("listTickers", this.tickers);
        editor.commit();

    }
}


