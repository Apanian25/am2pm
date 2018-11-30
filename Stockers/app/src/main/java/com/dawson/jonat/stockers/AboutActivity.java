package com.dawson.jonat.stockers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.dawson.jonat.stockers.Menu.Menus;
/**
 * Class responsible for displaying information about the app,
 * how to use, the apps purpose and its developers
 * By cliking on a developer, a dialog will pop up with the git name of that developer
 *
 * @author Lara Mezirovsky
 * @version 1.0
 */
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
        //only if another item on the menu is selected
        if(isOptionSelected)
            finish();

    }

    /**
     * Must override the option menu items - don't let the user click on the about option to launch
     * the about activity from the about page itself
     * @param
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.getItem(0).setEnabled(false);
        return true;
    }

    /**
     * This method pops up a dialog with the gitlab name of the developer according to the view id.
     * For example: if view-id is lara, lara's git lab name will be shown in the dialog
     * @param view
     */
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
