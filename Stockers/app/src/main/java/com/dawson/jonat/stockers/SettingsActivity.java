package com.dawson.jonat.stockers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dawson.jonat.stockers.Menu.Menus;

/**
 * Class responsible for letting the user enter info such as: first name, last name, email, password
 * and lets the user to chose the preferred currency and the preferred stock.
 * The user will be able to see the date he last modified his personal information, if he never modified it, the date
 * will be the "registration" date
 * @author Lara Mezirovsky
 * @version 1.0
 */
public class SettingsActivity extends Menus  implements AdapterView.OnItemSelectedListener{
    private String fname, lname, email, password, curr, stock, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //initialize spinner
        createSpinner(R.id.pref_curr, R.array.spinner_values_currency); //params: id of the spinner, string-array with values
        createSpinner(R.id.pref_stock, R.array.spinner_values_stock); //params: id of the spinner, string-array with values

        //get values from shared preferences
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
        //ask how to cancle - finish
        //todo ask tricia and my team
    }

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
     * they wish to save their changes when quiting the activity/without pressing on the save btn
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

    /**
     * Helper method to create a spinner
     * @param id -> spinner id (either currency spinner or stock spinner)
     * @param array -> spinner string-array items that represent either the currencies or the stocks
     */
    private void createSpinner(int id, int array){
        Spinner spinner = findViewById(id); //find
        //create an array adapter using the pre-defined spinner layout in android
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, array, android.R.layout.simple_spinner_item);
        //init
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       //store in shared pref.
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    //log nothing was selected
    }

    /**
     * As soon as the user clicks on the save button/ or on the OK option in the dialog before quiting the page,
     * the information will be stored in the shared preference
     * A method from the outside will check if to call this method
     * For ex: this method wont be called if no changed were detected
     */
    public void saveInfo(View view){
       SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
       SharedPreferences.Editor editor = prefs.edit();
        fname = ((EditText)findViewById(R.id.fname)).getText().toString();
        lname = ((EditText)findViewById(R.id.lname)).getText().toString();
        email = ((EditText)findViewById(R.id.email)).getText().toString();
        password = ((EditText)findViewById(R.id.password)).getText().toString();
        curr = ((Spinner)findViewById(R.id.pref_curr)).getSelectedItem().toString();
        stock = ((Spinner)findViewById(R.id.pref_stock)).getSelectedItem().toString();
        date = ((TextView)findViewById(R.id.date)).getText().toString();

        //store first name
        editor.putString("fname", fname);
        // store last name
        editor.putString("lname", lname);
        // store email
        editor.putString("email", email);
        // store password
        editor.putString("password", password);
        // store currency
        editor.putString("curr", fname);
        // store stock
        editor.putString("stock", fname);
        // store date
        editor.putString("date", fname);
    }

}
