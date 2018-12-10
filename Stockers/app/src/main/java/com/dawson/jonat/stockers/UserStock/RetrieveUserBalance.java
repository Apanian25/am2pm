package com.dawson.jonat.stockers.UserStock;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetrieveUserBalance implements OnCompleted {
    private Context context;
    private String bearerToken;
    private TextView balanceView;

    /**
     * Constructor that ensure sets the values of the context, bearer token and the balance text view
     *
     * @param context
     * @param bearerToken
     * @param balanceView
     */
    public RetrieveUserBalance(Context context, String bearerToken, TextView balanceView) {
        this.context = context;
        this.bearerToken = bearerToken;
        this.balanceView = balanceView;
    }

    /**
     * Creates a simpleApiCaller and uses it to execute a ApiUserThread that will query the php api
     * in order to get the amount of cash the user currently has left.
     */
    public void getCashAndDisplay() {
        String route = "http://stockers-web-app.herokuapp.com/api/api/cash";

        Map<String, String> params = new HashMap<>();

        SimpleAPICaller simpleAPICaller = new SimpleAPICaller(route, HttpMethods.GET, params, bearerToken);
        APIUserThread apiUserThread = new APIUserThread(this);
        apiUserThread.execute(simpleAPICaller);
    }

    /**
     * Responds to the response from the api and will display the balance that is left
     *
     * @param response
     */
    @Override
    public void OnTaskCompleted(SimpleAPIResponse response) {
        String balance = getBalanceFromResponse(response.getMessageBody());

        balanceView.setText(context.getResources().getString(R.string.balance) + " " + balance);
    }


    /**
     * Decodes the json to get the amount of cash that the user has left.
     *
     * @param jsonString
     * @return
     */
    private String getBalanceFromResponse(String jsonString) {
        try {
            return new JSONObject(jsonString).getString("cash");
            //JsonObject jsonObject = new JsonObject(jsonString).get("cash").getString("cash");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "???";
    }
}
