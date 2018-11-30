package com.dawson.jonat.stockers;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class CurrencyExchange extends Activity {

    private TextView connectivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_exchange);

        connectivity = (TextView) findViewById(R.id.connectivity);

        checkConnectivity();
    }

    private void checkConnectivity() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Connection is established
            // invoke the AsyncTask to do the dirtywork.
            connectivity.setText("Connection available.");
        } else {
            //There is no Connection
            connectivity.setText("No network connection available.");
        }
    }


}
