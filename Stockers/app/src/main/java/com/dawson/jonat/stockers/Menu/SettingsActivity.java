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
public class SettingsActivity extends Menus {
    protected boolean isBackKey;
    Spinner spinner_curr, spinner_stock;
    EditText fname, lname, email, password;
    TextView last_mod_date;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //initialize
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        spinner_curr = findViewById(R.id.pref_curr);
        spinner_stock = findViewById(R.id.pref_stock);
        createSpinner(spinner_curr, R.array.spinner_values_currency); //params: spinner, string-array with values
        createSpinner(spinner_stock, R.array.spinner_values_stock); //params: spinner, string-array with values
        last_mod_date = findViewById(R.id.date);
        //get values from shared preferences
        loadInfo();
    }

    /**
     *  Must override the onPause method - As soon as the user clicks back or on another
     *  menu option, the activity will askt the user if he wishes to save his changes (if any were detected)
     *  As well, the current activity will close itself in order not to pile up the back stack.\
     */
    @Override
    public void onPause(){
        super.onPause();
        //check if user quits the page by selecting another option on the menu or back button
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
                    saveInfoHelper();
                }
            });
            builder.setNegativeButton(R.string.btn_text_settings_cancle, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //todo: nothing?
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                                                                     array,
                                                                                     android.R.layout.simple_spinner_item);
        //init
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    /**
     * As soon as the user clicks on the save button/ or on the OK option in the dialog before quiting the page,
     * the information will be stored in the shared preference
     * A method from the outside will check if to call this method
     * For ex: this method wont be called if no changed were detected
     */
    public void saveInfo(View view){
       saveInfoHelper();
    }

    /**
     * Private helper method to save fname, lname, email, password, currency, stock exchange and date in
     * shared preferences
     */
    private void saveInfoHelper(){
        //store first name
        editor.putString("fname", fname.getText().toString());
        // store last name
        editor.putString("lname", lname.getText().toString());
        // store email
        editor.putString("email", email.getText().toString());
        // store password
        editor.putString("password", password.getText().toString());
        // store currency
        editor.putInt("curr", spinner_curr.getSelectedItemPosition());
        // store stock
        editor.putInt("stock", spinner_stock.getSelectedItemPosition());
        // store date
        editor.putString("date", last_mod_date.getText().toString());

        editor.commit();

        Toast.makeText(this, R.string.data_saved, Toast.LENGTH_SHORT).show();

        loadInfo(); //to show new data immediately without refreshing the page
    }

    /**
     * Helper method used to load shared preferences
     */
    private void loadInfo(){
        fname.setText(prefs.getString("fname", null));
        lname.setText(prefs.getString("lname", null));
        email.setText(prefs.getString("email", null));
        password.setText(prefs.getString("password", null));
        spinner_curr.setSelection(prefs.getInt("curr", 0));
        spinner_stock.setSelection(prefs.getInt("stock", 0));
        last_mod_date.setText(prefs.getString("date", null));
    }

    /**
     * Private helper method that checks if user's input is different from what is stored in shared pref.
     * @return
     */
    private boolean checkIfChanged() {
        //todo make better synatx
        //get everything
        if (!(fname.getText().toString().equals(prefs.getString("fname", null)) ||
                lname.getText().toString().equals(prefs.getString("lname", null)) ||
                email.getText().toString().equals(prefs.getString("email", null)) ||
                password.getText().toString().equals(prefs.getString("password", null)) ||
                spinner_curr.getSelectedItemPosition() == (prefs.getInt("curr", 0)) ||
                spinner_stock.getSelectedItemPosition() == (prefs.getInt("stock", 0)))) {
            return true;
        }
       return false;
    }


    /**
     * Helper method that will determine if the back key was pressed
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.d(this.getClass().getName(), "back button pressed");
            isBackKey = true;
        }
        return isBackKey;
    }
}
