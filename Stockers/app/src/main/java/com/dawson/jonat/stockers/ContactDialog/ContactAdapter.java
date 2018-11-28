package com.dawson.jonat.stockers.ContactDialog;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dawson.jonat.stockers.R;

public class ContactAdapter extends BaseAdapter {
    private Context contextOfView;
    private ContactInformation[] contacts;
    private LayoutInflater infalter;

    /**
     * Initializes the context, contacts and will use the context to create the inflater
     *
     * @param context
     * @param contacts
     */
    public ContactAdapter(Context context, ContactInformation[] contacts) {
        this.contextOfView = context;
        this.infalter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * This method is used by android in order to display the elements within the list view
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = new ViewHolder();
        View row = convertView;

        if (row == null) {
            row = this.infalter.inflate(R.layout.contact_view, null);
            row.setTag(vh);
        } else {
            vh = (ViewHolder)convertView.getTag();
        }

        vh.textView = (TextView)row.findViewById(R.id.contactName);
        vh.textView.setText(contacts[position].contactName);

        vh.imageView = (ImageView)row.findViewById(R.id.icon);
        if (contacts[position].getImage() != null) {
            Uri uri = Uri.parse(contacts[position].getImage());
            vh.imageView.setImageURI(uri);
        } else {
            vh.imageView.setImageResource(R.drawable.icon);
        }

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send the email to this person
                Toast.makeText(contextOfView, "Was clicked", Toast.LENGTH_LONG).show();
            }
        });

        return row;
    }
}

//Class that is used to hold the textView, imageView and imageUrl in the tag of a row
class ViewHolder {
    TextView textView;
    ImageView imageView;
}