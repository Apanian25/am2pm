package com.dawson.jonat.stockers.ContactDialog;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawson.jonat.stockers.LoanCalculator.LoanPayoutSummary;
import com.dawson.jonat.stockers.R;

import java.util.List;

public class ContactRecyclerView extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<ContactInformation> contacts;
    private LayoutInflater inflater;
    private LoanPayoutSummary summary;

    /**
     * Initialize the Context that launced the recycler view, the contacts to be displayed and the
     * LoanPayoutSummary to be sent in the email
     *
     * @param context
     * @param contacts
     * @param summary
     */
    public  ContactRecyclerView(Context context, List<ContactInformation> contacts, LoanPayoutSummary summary) {
        this.inflater = LayoutInflater.from(context);
        this.contacts = contacts;
        this.summary = summary;
        this.context = context;
    }

    /**
     * Inflates the view and creates a view holder with it
     *
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = inflater.inflate(R.layout.contact_view, viewGroup, false);
        return new ViewHolder(view, this, context);
    }

    /**
     * Binds the view holder with the contact data from that position
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.textViewEmail.setText(contacts.get(position).getEmailAddress());
        viewHolder.textViewName.setText(contacts.get(position).getContactName());
        if (contacts.get(position).getImage() != null) {
            Uri uri = Uri.parse(contacts.get(position).getImage());
            viewHolder.imageView.setImageURI(uri);
        } else {
            viewHolder.imageView.setImageResource(R.drawable.icon);
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    /**
     * Retrieves the LoanPayoutSummary
     * @return
     */
    public LoanPayoutSummary getSummary() {
        return summary;
    }

}
