package com.dawson.jonat.stockers.ContactDialog;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.ListView;

import com.dawson.jonat.stockers.R;

public class ContactUtilities {

    public static void launchDialogToSendEmail(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.contact_dialog);
        dialog.setTitle("Contacts");

        ContactInformation[] contacts = new ContactInformation[20];

        ContentResolver resolver = context.getContentResolver();
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
        listView.setAdapter(new ContactAdapter(context, contacts));
        dialog.show();
    }

    private static String getStringFromContacts(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

}
