package com.dawson.jonat.stockers.StockQuote;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dawson.jonat.stockers.Entity.Ticker;
import com.dawson.jonat.stockers.R;

import java.util.ArrayList;
/**
 * Adapter responsible to create a recycler view holding up to 5 ticker symbols
 * If user clicks on trash - will delete that item + update the list
 * If user clicks on the view - an intent will get launched to see more info about that stock
 * @author Lara Mezirovsky
 * @version 1.0
 */
public class TickerAdapter extends RecyclerView.Adapter<TickerAdapter.TickerHolder>{

    Context c;
    ArrayList<Ticker> tickers;

    /**
     * Init context (StockQuotesActivity) and list of ticker symbols from the user
     * @param context
     * @param list
     */
    public TickerAdapter(Context context, ArrayList<Ticker> list){
        c = context;
        tickers = list;
    }

    @Override
    public TickerHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(c).inflate(R.layout.recyclerview_ticker_item, parent, false);
        return new TickerHolder(v);
    }

    @Override
    public void onBindViewHolder(final TickerHolder tickerHolder, int i) {
       Ticker t = tickers.get(i);
       tickerHolder.tickerSymbol.setText(t.getSymbol());
       tickerHolder.sub.setImageResource(t.getImageSource());
        tickerHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Tag", "HEREEEEEEEEEEE");//todo to fix:(
                Intent i = new Intent(c,ShowStockActivity.class);
                i.putExtra("ticker", tickers.get(tickerHolder.getAdapterPosition()).getSymbol());
            }
        });
    }

    @Override
    public int getItemCount() {
        return tickers.size();
    }


    public class TickerHolder extends RecyclerView.ViewHolder{

        TextView tickerSymbol;
        ImageView sub;
        LinearLayout layout;
        public TickerHolder(View itemView) {
            super(itemView);
            tickerSymbol = itemView.findViewById(R.id.tickerSymbol);
            sub = itemView.findViewById(R.id.sub);
            layout = itemView.findViewById(R.id.view);
        }
    }
}
