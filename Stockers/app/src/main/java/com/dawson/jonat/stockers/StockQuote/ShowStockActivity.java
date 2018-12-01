package com.dawson.jonat.stockers.StockQuote;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dawson.jonat.stockers.Menu.Menus;
import com.dawson.jonat.stockers.R;

public class ShowStockActivity extends Menus {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(checkNetwrok()) {
            setContentView(R.layout.activity_show_stock);
        }
        else{
            setContentView(R.layout.error_page);
        }
    }

    ConnectivityManager connectionManager; //Class that answers queries about the state of network connectivity.
    NetworkInfo netInfo; //Describes the status of a network interface.



    /**
     * Helper method to check network connectivity
     */
    public boolean checkNetwrok() {
        //init a connectionManager object
        connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //We dont care if its is wifi or mobile, we want to user the available network for us
        netInfo = connectionManager.getActiveNetworkInfo();
        /**
         * About permission:
         * uses-permission -> requests some permission
         * permission -> what allows
         */
        if (netInfo != null /**has been instantiated**/ && netInfo.isConnected()) {
            Log.i("Tag", "This is our net info--->    " + netInfo);
            return true;


        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
