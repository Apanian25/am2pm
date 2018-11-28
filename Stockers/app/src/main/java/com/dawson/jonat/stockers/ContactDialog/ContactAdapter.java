package com.dawson.jonat.stockers.ContactDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


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

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send the email to this person
            }
        });

        return row;
    }
}

//Class that is used to hold the textView, imageView and imageUrl in the tag of a row
class ViewHolder {
    TextView textView;
    String imageUrl;
}