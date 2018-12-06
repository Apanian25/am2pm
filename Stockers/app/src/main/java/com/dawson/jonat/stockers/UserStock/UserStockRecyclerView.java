package com.dawson.jonat.stockers.UserStock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dawson.jonat.stockers.UserStock.ViewHolder;
import com.dawson.jonat.stockers.R;

import java.util.List;

/**
 * Recycler view that is used to display the users stocks
 */
public class UserStockRecyclerView extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<StockInformation> stocks;
    private LayoutInflater inflater;


    /**
     * Initialize the Context that launced the recycler view, the list of newsInformation to be
     * displayed
     *
     * @param context
     */
    public  UserStockRecyclerView(Context context, List<StockInformation> stocks) {
        this.inflater = LayoutInflater.from(context);
        this.stocks = stocks;
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
        View view = inflater.inflate(R.layout.user_stock_layout, viewGroup, false);
        return new ViewHolder(view, context);
    }

    /**
     * Binds the view holder with the users stock information data from that position
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.textViewTickerSymbol.setText(stocks.get(position).getTickerSymbol());
        viewHolder.textViewPurchasePrice.setText(context.getResources().getString(R.string.purchasePrice) + stocks.get(position).getPurchasePrice());
        viewHolder.textViewQuantity.setText(context.getResources().getString(R.string.quantity) + stocks.get(position).getQuantity());
    }

    /**
     * Return number of elements within the recycler view
     * @return
     */
    @Override
    public int getItemCount() {
        return stocks.size();
    }
}
