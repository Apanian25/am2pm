package com.dawson.jonat.stockers.ContactDialog;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.*;

import java.util.ArrayList;
import java.util.List;

public class temp {

    Context context;

    private ContactInformation[] getContactInformation() {
        Cursor contacts = getContacts();
        List<ContactInformation> contactList = new ArrayList<>();

        while (contacts.moveToNext()) {
            String name = getStringFromContacts(contacts, ContactsContract.Contacts.DISPLAY_NAME);
            String imageUri = getStringFromContacts(contacts, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI);
            String id = getStringFromContacts(contacts, ContactsContract.Contacts._ID);
            String email = getEmailAddress(id);

            if (email != null)
                contactList.add(new ContactInformation(name, imageUri, email));
        }
        return (ContactInformation[])contactList.toArray();
    }

    private Cursor getContacts() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
        };

        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = "
                + ("1") + "";
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME +
                "ASC";
        return context.getContentResolver().query(uri, projection, selection, null, sortOrder );
    }

    private String getEmailAddress(String contactId) {
        Uri uri = Email.CONTENT_URI;
        String address;
        String selection = Email.CONTACT_ID + " = '" + contactId + "'";

        Cursor emails = context.getContentResolver().query(uri, null, selection, null, null);

        if (emails.moveToFirst()) {
            address = getStringFromContacts(emails, Email.ADDRESS);
        } else {
            address = null;
        }

        emails.close();
        return address;
    }


    /**
     * This function will simply query the database to retrieve a string from a specified column
     * @param cursor
     * @param column
     * @return
     */
    private static String getStringFromContacts(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }
}
