package com.dawson.jonat.stockers.ContactDialog;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.dawson.jonat.stockers.LoanCalculator.LoanPayoutSummary;
import com.dawson.jonat.stockers.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ContactUtilities {

    /**
     * This method will create and launch a dialog containing a list view with all of the contacts
     * information, allowing the user to choose one. If the user clicks on a contact, an Email will
     * be sent to them.
     *
     * @param context Activity for the launch to be launced in
     */
    public static void launchDialogToSendEmail(Context context, LoanPayoutSummary summary) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.contact_dialog);
        dialog.setTitle("Contacts");

        List<ContactInformation> contacts = getContactInformation(context);

        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.contactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new ContactRecyclerView(context, contacts, summary));
        dialog.show();
    }


    /**
     * Retrieves all of the contacts as an array of ContactInformation
     *
     * @param context
     * @return
     */
    private static List<ContactInformation> getContactInformation(Context context) {
        Cursor contacts = getContacts(context);
        List<ContactInformation> contactList = new ArrayList<>();

        while (contacts.moveToNext()) {
            String name = getStringFromContacts(contacts, ContactsContract.Contacts.DISPLAY_NAME);
            String imageUri = getStringFromContacts(contacts, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI);
            String id = getStringFromContacts(contacts, ContactsContract.Contacts._ID);
            String email = getEmailAddress(id, context);

            if (email != null)
                contactList.add(new ContactInformation(name, imageUri, email));
        }

        return contactList;
    }

    /**
     * Retrieves a cursor containing all of the contacts displayName, id and thumbnail uri
     *
     * @param context
     * @return
     */
    private static Cursor getContacts(Context context) {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
        };

        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = "
                + ("1") + "";
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME +
                " ASC";
        return context.getContentResolver().query(uri, projection, selection, null, sortOrder );
    }

    /**
     * Retrieves the email address of a contact based on the contacts id
     *
     * @param contactId
     * @param context
     * @return
     */
    private static String getEmailAddress(String contactId, Context context) {
        Uri uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String address;
        String selection = ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = '" + contactId + "'";

        Cursor emails = context.getContentResolver().query(uri, null, selection, null, null);

        if (emails.moveToFirst()) {
            address = getStringFromContacts(emails, ContactsContract.CommonDataKinds.Email.ADDRESS);
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
