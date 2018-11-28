package com.dawson.jonat.stockers;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;

import com.dawson.jonat.stockers.ContactDialog.ContactAdapter;
import com.dawson.jonat.stockers.ContactDialog.ContactInformation;
import com.dawson.jonat.stockers.LoanCalculator.LoanCalculatorActivity;

import java.io.InputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_dialog);
        startActivity(new Intent(this, LoanCalculatorActivity.class));
        launchDialog();

        ContactInformation[] contacts = new ContactInformation[20];

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        int counter = 0;

        while (cursor.moveToNext() && counter < 20) {
            String name = getStringFromContacts(cursor, ContactsContract.Contacts.DISPLAY_NAME);
            String imageUri = getStringFromContacts(cursor, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI);
            //String emailAddress = getStringFromContacts(cursor, ContactsContract.CommonDataKinds.Email.DATA);

            contacts[counter] = new ContactInformation(name, imageUri, "Email Is Not Implemented Yet");
            counter++;
        }
        cursor.close();

        ListView listView = (ListView)findViewById(R.id.contactList);
        listView.setAdapter(new ContactAdapter(this, contacts));
    }

    public void launchDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.contact_dialog);
        dialog.setTitle("Contacts");

        ContactInformation[] contacts = new ContactInformation[20];

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        int counter = 0;

        while (cursor.moveToNext() && counter < 20) {
            String name = getStringFromContacts(cursor, ContactsContract.Contacts.DISPLAY_NAME);
            String imageUri = getStringFromContacts(cursor, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI);
            //String emailAddress = getStringFromContacts(cursor, ContactsContract.CommonDataKinds.Email.DATA);

            contacts[counter] = new ContactInformation(name, imageUri, "Email Not Implemented");
            counter++;
        }
        cursor.close();

        ListView listView = (ListView)dialog.findViewById(R.id.contactList);
        listView.setAdapter(new ContactAdapter(this, contacts));
        dialog.show();
    }

    public String getStringFromContacts(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }
}
