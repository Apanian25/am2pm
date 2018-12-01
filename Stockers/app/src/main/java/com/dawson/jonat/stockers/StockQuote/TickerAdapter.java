package com.dawson.jonat.stockers.StockQuote;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawson.jonat.stockers.R;

import java.util.ArrayList;

public class TickerAdapter extends RecyclerView.Adapter<TickerAdapter.TickerHolder>{

    Context c;
    ArrayList<Ticker> tickers;

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
    public void onBindViewHolder(TickerHolder tickerHolder, int i) {
       Ticker t = tickers.get(i);
       tickerHolder.tickerSymbol.setText(t.getSymbol());
       tickerHolder.sub.setImageResource(t.getImageSource());
    }

    @Override
    public int getItemCount() {
        return tickers.size();
    }


    public class TickerHolder extends RecyclerView.ViewHolder{

        TextView tickerSymbol;
        ImageView sub;
        public TickerHolder(View itemView) {
            super(itemView);
            tickerSymbol = itemView.findViewById(R.id.tickerSymbol);
            sub = itemView.findViewById(R.id.sub);
        }
    }
}
