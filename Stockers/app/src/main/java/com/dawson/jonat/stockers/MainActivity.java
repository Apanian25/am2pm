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
import com.dawson.jonat.stockers.ContactDialog.ContactInformation;
import com.dawson.jonat.stockers.LoanCalculator.LoanCalculatorActivity;

import java.io.InputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_dialog);
        startActivity(new Intent(this, LoanCalculatorActivity.class));
    }
}
