package com.dawson.jonat.stockers.Menu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.dawson.jonat.stockers.AboutActivity;
import com.dawson.jonat.stockers.R;
import com.dawson.jonat.stockers.SettingsActivity;

import java.net.URI;

public class Menus extends AppCompatActivity {
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
     * Giving functionality to each option in the menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                launchAboutActivity();
                return true;
            case R.id.dawson_website:
                launchDawsonWebsiteActivity();
            case R.id.settings:
                launchSettingsActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Helper method to launch an explicit intent - about activity
     */
    private void launchAboutActivity() {
        Intent i = new Intent(this, AboutActivity.class);
        startActivity(i);
    }

    /**
     * Helper method to launch an implicit intent - dawson college official website
     */
    private void launchDawsonWebsiteActivity() {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(this.getString(R.string.urlToDawson)));
        startActivity(i);
    }

    /**
     * Helper method to launch an implicit intent - setting activity
     * todo: put about and settings in one method to avoid repetition!
     */
    private void launchSettingsActivity(){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }
}//end class