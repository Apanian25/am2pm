package com.dawson.jonat.stockers.ContactDialog;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dawson.jonat.stockers.Email.Email;
import com.dawson.jonat.stockers.LoanCalculator.LoanPayoutSummary;
import com.dawson.jonat.stockers.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final String SUBJECT = "Credit Loan Calculation Results";
    private Context contextOfView;
    private ContactRecyclerView adapter;

    TextView textViewName;
    TextView textViewEmail;
    ImageView imageView;

    /**
     * Users
     *
     * @param view
     * @param adapter
     * @param context
     */
    public ViewHolder(View view, ContactRecyclerView adapter, Context context) {
        super(view);
        textViewName = (TextView)view.findViewById(R.id.contactName);
        textViewEmail = (TextView)view.findViewById(R.id.emailAddress);
        imageView = (ImageView)view.findViewById(R.id.icon);
        view.setOnClickListener(this);

        this.adapter = adapter;
        this.contextOfView = context;
    }

    /**
     * When an view holder is clicked, it will launch an intent to send an email
     * @param v
     */
    @Override
    public void onClick(View v) {
        //Send the email to this person
        Email.sendEmail(this.textViewEmail.getText().toString(), SUBJECT, adapter.getSummary(), contextOfView);
    }
}
