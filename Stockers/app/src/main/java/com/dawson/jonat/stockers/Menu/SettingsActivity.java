package com.dawson.jonat.stockers.Menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dawson.jonat.stockers.Menu.Menus;
import com.dawson.jonat.stockers.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class responsible for letting the user enter info such as: first name, last name, email, password
 * and lets the user to chose the preferred currency and the preferred stock.
 * The user will be able to see the date he last modified his personal information, if he never modified it, the date
 * will be the "registration" date
 * @author Lara Mezirovsky
 * @version 1.0
 */
public class SettingsActivity extends Menus  implements AdapterView.OnItemSelectedListener{
    private String fname, lname, email, password, date;
    private int curr, stock;
    private Spinner spinner_curr, spinner_stock;
    protected boolean isBackKey;
    protected boolean prefChanged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //initialize spinner
        Spinner spinner_curr = findViewById(R.id.pref_curr);
        Spinner spinner_stock = findViewById(R.id.pref_stock);
        createSpinner(spinner_curr, R.array.spinner_values_currency); //params: spinner, string-array with values
        createSpinner(spinner_stock, R.array.spinner_values_stock); //params: spinner, string-array with values


        //get values from shared preferences
        loadInfo();
    }

    /**
     *  Must override the onPause method - the about activity will create/recreate
     * an instance of it self when clicking on the settings option.
     * As soon as the user clicks back, the activity will close itself, asking the user
     * if he/she wishes to save his changes/if applicable
     */
    @Override
    public void onPause(){
        super.onPause();
        //check if user quits the page by selecting another option on the menu or back button
        Log.i("TGA", "Those are the values--->" + isOptionSelected +" option selected" + isBackKey + " is back key" + prefChanged + "are prefs changed");
        if((isOptionSelected || isBackKey)){
            //check if something has changes
            if(checkIfChanged()){
                showDialog();
            }
            else{
                finish();
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.d(this.getClass().getName(), "back button pressed");
            isBackKey = true;

        }
        return isBackKey;
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
                    Log.i("CANCLED", "Cancel");
                }
            });

            builder.show(); //todo ask

        }

    /**
     * Helper method to create a spinner
     * @param spinner -> either currency spinner or stock spinner)
     * @param array -> spinner string-array items that represent either the currencies or the stocks
     */
    private void createSpinner(Spinner spinner, int array){
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
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        Spinner spinner_curr = findViewById(R.id.pref_curr);
        Spinner spinner_stock = findViewById(R.id.pref_stock); ////////////ASK WHY HERE?
        fname = ((EditText)findViewById(R.id.fname)).getText().toString();
        lname = ((EditText)findViewById(R.id.lname)).getText().toString();
        email = ((EditText)findViewById(R.id.email)).getText().toString();
        password = ((EditText)findViewById(R.id.password)).getText().toString();
        curr = spinner_curr.getSelectedItemPosition(); // we are storing the position and not the string
        stock = spinner_stock.getSelectedItemPosition(); // we are storing the position and not the string
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        //store first name
        editor.putString("fname", fname);
        // store last name
        editor.putString("lname", lname);
        // store email
        editor.putString("email", email);
        // store password
        editor.putString("password", password);
        // store currency
        editor.putInt("curr", curr);
        // store stock
        editor.putInt("stock", stock);
        // store date
        editor.putString("date", date);

       editor.commit();

        Toast.makeText(this, R.string.data_saved, Toast.LENGTH_SHORT).show();

        loadInfo(); //to show new
    }

    /**
     * Helper method used to load shared preferences
     */
    private void loadInfo(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ((EditText)findViewById(R.id.fname)).setText(prefs.getString("fname", null));
        ((EditText)findViewById(R.id.lname)).setText(prefs.getString("lname", null));
        ((EditText)findViewById(R.id.email)).setText(prefs.getString("email", null));
        ((EditText)findViewById(R.id.password)).setText(prefs.getString("password", null));
        ((Spinner)findViewById(R.id.pref_curr)).setSelection(prefs.getInt("curr", 0));
        ((Spinner)findViewById(R.id.pref_stock)).setSelection(prefs.getInt("stock", 0));
        ((TextView)findViewById(R.id.date)).setText(getString(R.string.date) +  " " + prefs.getString("date", null));
    }

    private boolean checkIfChanged() {
        //todo make better synatx
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //get everything
        if (!((EditText) findViewById(R.id.fname)).getText().toString().equals(prefs.getString("fname", null))) {
            return true;
        }
       return false;
    }

//        }
//        ((EditText)findViewById(R.id.fname)).setText(prefs.getString("fname", null));
//        ((EditText)findViewById(R.id.lname)).setText(prefs.getString("lname", null));
//        ((EditText)findViewById(R.id.email)).setText(prefs.getString("email", null));
//        ((EditText)findViewById(R.id.password)).setText(prefs.getString("password", null));
//        ((Spinner)findViewById(R.id.pref_curr)).setSelection(prefs.getInt("curr", 0));
//        ((Spinner)findViewById(R.id.pref_stock)).setSelection(prefs.getInt("stock", 0));
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//
//        ((EditText)findViewById(R.id.fname)).setText(prefs.getString("fname", null));
//        ((EditText)findViewById(R.id.lname)).setText(prefs.getString("lname", null));
//        ((EditText)findViewById(R.id.email)).setText(prefs.getString("email", null));
//        ((EditText)findViewById(R.id.password)).setText(prefs.getString("password", null));
//        ((Spinner)findViewById(R.id.pref_curr)).setSelection(prefs.getInt("curr", 0));
//        ((Spinner)findViewById(R.id.pref_stock)).setSelection(prefs.getInt("stock", 0));
//
//    }
}
