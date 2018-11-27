package com.dawson.jonat.stockers.LoanCalculator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.dawson.jonat.stockers.R;

public class LoanCalculatorActivity extends Activity {

    Spinner spinner;
    EditText amount, interestRate, minPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_calculator);

        this.initializeViews();
        this.fillYearSpinner();
    }

    /**
     * Helper method that will initialize all of the views
     */
    private void initializeViews() {
        this.spinner = (Spinner) findViewById(R.id.spinnerYears);
        this.amount = (EditText) findViewById(R.id.amount);
        this.interestRate = (EditText) findViewById(R.id.interestRate);
        this.minPayment = (EditText) findViewById(R.id.minPayment);
    }

    private void fillYearSpinner() {
        //Create the array adapter that will be userd for the spinner
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.yearOptions, android.R.layout.simple_spinner_item);

        //ff
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(arrayAdapter);
    }
}
