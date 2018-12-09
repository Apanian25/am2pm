package com.dawson.jonat.stockers.UserStock;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dawson.jonat.stockers.APIUtil.APIUserThread;
import com.dawson.jonat.stockers.APIUtil.HttpMethods;
import com.dawson.jonat.stockers.APIUtil.OnCompleted;
import com.dawson.jonat.stockers.APIUtil.SimpleAPICaller;
import com.dawson.jonat.stockers.APIUtil.SimpleAPIResponse;
import com.dawson.jonat.stockers.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetrieveUserStock implements OnCompleted {
    private Context context;
    private String bearerToken;
    private RecyclerView recyclerView;

    public RetrieveUserStock(Context context, String bearerToken, RecyclerView recyclerView) {
        this.context = context;
        this.bearerToken = bearerToken;
        this.recyclerView = recyclerView;
    }

    public void getStocksAndDisplay() {
        String route = "http://stockers-web-app.herokuapp.com/api/api/allstocks";

        Map<String, String> params = new HashMap<>();

        SimpleAPICaller simpleAPICaller = new SimpleAPICaller(route, HttpMethods.GET, params, bearerToken);
        APIUserThread apiUserThread = new APIUserThread(this);
        apiUserThread.execute(simpleAPICaller);
    }

    @Override
    public void OnTaskCompleted(SimpleAPIResponse response) {
        List<StockInformation> stocks = getStocksFromResponse(response.getMessageBody());

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new UserStockRecyclerView(context, stocks));
    }

    private List<StockInformation> getStocksFromResponse(String jsonString) {
        List<StockInformation> stockInformations = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            //JsonObject jsonObject = new JsonObject(jsonString);
            //JSONArray jsonArray = jsonObject.getJSONArray("content");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                stockInformations.add(new StockInformation(jsonObject.getString("tickerSymbol"),
                        jsonObject.getString("quantity"), jsonObject.getString("priceBought")));
            }

        } catch (JSONException e) {
            Toast.makeText(context, context.getResources().getString(R.string.errorJson) ,Toast.LENGTH_LONG);
        }

        return stockInformations;
    }
}
