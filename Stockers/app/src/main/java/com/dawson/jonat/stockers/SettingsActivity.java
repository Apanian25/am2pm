package com.dawson.jonat.stockers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.dawson.jonat.stockers.Menu.Menus;

public class SettingsActivity extends Menus {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //initialize spinner

    }
    /**
     * private helper method to initialize spinner
     */
    private void createSpinner(){
    }

    /**
     *  Must override the onPause method - the about activity will create/recreate
     * an instance of it self when clicking on the settings option.
     * As soon as the user clicks back, the activity will close itself, asking the user
     * if he/she wishes to save his changes/if applicable
     */
    @Override
    public void onPause(){
        //dialog will launch if some changes were detected todo
        super.onPause();
        showDialog();
        //ask how to cancle
    }
    //todo doc!

    /**
     * Must override the option menu items - don't let the user click on the setting option to launch
     * the setting activity from the setting page itself
     * @param
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.getItem(2).setEnabled(false);
        return true;
    }

    /**
     * private helper method to show a dialog asking a user if
     * they wish to save their changes when quiting the acitivty/without pressing on the save btn
     */
    private void showDialog(){
            //declare a dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.setting_dialog_title);
            builder.setMessage(R.string.setting_dialog_message);
            //close alert when user clicks ok
            builder.setPositiveButton(R.string.btn_text, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //will be saved in shared pref
                }
            });
            builder.setNegativeButton(R.string.btn_text_settings_cancle, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //will cancle
                    Log.i("CANCLED", "Cancle");
                }
            });

            builder.show();

        }
}