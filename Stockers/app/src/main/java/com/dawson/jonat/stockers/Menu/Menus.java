package com.dawson.jonat.stockers.Menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.dawson.jonat.stockers.R;

/**
 * Class responsible for displaying an Options menu with 3 choices: About -->
 * info about app and the team Go To Dawson - redirects the user to the dawson
 * website using an implicit intent Settings --> the user can edit his personal
 * info
 *
 * @author Lara Mezirovsky
 * @version 1.0
 */
public class Menus extends AppCompatActivity {

    protected boolean isOptionSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Inflating the main_menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Giving functionality to each option in the menu - launching other
     * activities Note: view documentation in activities for functionality
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        isOptionSelected = true;
        switch (item.getItemId()) {
            case R.id.about:
                intent = new Intent(this, AboutActivity.class);
                launchActivity(intent);
                return isOptionSelected;
            case R.id.dawson_website:
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(this.getString(R.string.urlToDawson)));
                launchActivity(intent);
                return isOptionSelected;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                launchActivity(intent);
                return isOptionSelected;
            default:
                isOptionSelected = super.onOptionsItemSelected(item);
                return isOptionSelected;
        }
    }

    /**
     * Helper method to launch an intent
     */
    protected void launchActivity(Intent i) {
        startActivity(i);
    }
}
