package com.dawson.jonat.stockers.StockQuote;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
/**
 * Class responsible for displaying for creating a recycler View that
 * gathers up to 5 ticker symbols that the user can view
 *
 * @author Lara Mezirovsky
 * @version 1.0
 */
public class StockQuotesActivity extends Menus {
    ArrayList<Ticker> list;
    EditText ed;
    RecyclerView rv;
    private TickerAdapter tickerAdapter;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_quotes);
        list = new ArrayList<>();
        //find edit text
        ed = findViewById(R.id.tickerInput);
        rv = findViewById(R.id.tickerList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
    }

    /**
     * private helper method that generates an array  of tickers
     * Max length: 5 tickers symbols inputed by the user
     * Cant input "" or space
     */
    private ArrayList<Ticker> getData() {
        //will be adding to array if size is less than 5
        if (list.size() < 5) {
            String text = ed.getText().toString();
            //check if not empty string
            if((!(text.equals("")) || text.equals(null))){
                list.add(new Ticker(text, R.drawable.trash, R.drawable.search));
                return list;
            }
            else{
                Toast.makeText(this, R.string.empty_string, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.more_than_five, Toast.LENGTH_SHORT).show();
        }
        return list;
    }

    public void addTicker(View view) {
        tickerAdapter = new TickerAdapter(this, getData());
        rv.setAdapter(tickerAdapter);
    }


}


