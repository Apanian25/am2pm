package com.dawson.jonat.stockers;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import com.dawson.jonat.stockers.Menu.Menus;

public class AboutActivity extends Menus {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    /**
     * Must override the onPause method - don't let the user call about activity from the about acitivty
     */
    @Override
    public void onPause(){
        super.onPause();

    }

    public void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_string)
                .setTitle(R.string.dialog_title);
        builder.create().show();
    }
}
