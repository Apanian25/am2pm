package com.dawson.jonat.stockers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dawson.jonat.stockers.Menu.Menus;

public class AboutActivity extends Menus {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    /**
     * Must override the onPause method - the about activity will create/recreate
     * an instance of it self when clicking on the about option.
     * As soon as the user clicks back, the activity will close itself
     */
    @Override
    public void onPause(){
        super.onPause();
        finish();
    }

    /**
     * Must override the option menu items - don't let the user click on the about option to launch
     * the about activity from the about page itself
     * @param true if successfully created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.getItem(0).setEnabled(false);
        return true;
    }

    public void showMoreInfo(View view) {
        //declare a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.about_dialog_title);

        switch (view.getId()){
            case R.id.john:
                builder.setMessage(R.string.git_john);
                break;
            case R.id.lara:
                builder.setMessage(R.string.git_lara);
                break;

            case R.id.nick:
                builder.setMessage(R.string.git_nick);
                break;
            case R.id.danny:
                builder.setMessage(R.string.git_danny);
                break;
        }

        //close alert when user clicks ok
        builder.setPositiveButton(R.string.btn_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog alert = builder.create();
        alert.show();
    }
}
