package com.dawson.jonat.stockers.LoanCalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dawson.jonat.stockers.R;

public class LoanCalculatorActivity extends AppCompatActivity {

    Spinner spinnerYears;
    EditText amountView, interestRateView, minPaymentView;
    LinearLayout resultsSpace;
    View resultsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_calculator);

        this.initializeViews();
        this.fillYearSpinner();
    }


    /**
     * Reads the user input and will then calculate the LoanPaymentResults, which will then be
     * displayed on the screen.
     *
     * @param view
     */
    public void calculateAndDisplayResults(View view) {
        try {
            double amount = Double.parseDouble(this.amountView.getText().toString());
            double interestRate = Double.parseDouble(this.interestRateView.getText().toString());
            double minPayment = Double.parseDouble(this.minPaymentView.getText().toString());
            int years = Integer.parseInt(spinnerYears.getSelectedItem().toString());

            this.displayResults(LoanCalculator.calculateInterestPayoutSummary(amount, interestRate, minPayment, years));
        } catch (Exception e) {
            Toast.makeText(this, "Please ensure that all the fields are filled in", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Displays the loanPayoutSummary on the table results
     * @param loanPayoutSummary
     */
    private void displayResults(LoanPayoutSummary loanPayoutSummary) {
        if (resultsTable == null) {
            resultsTable = (LinearLayout)this.getLayoutInflater().inflate(R.layout.loan_calculator_result_table,resultsSpace);
        }
        ((TextView)findViewById(R.id.timeToPayOff)).setText(loanPayoutSummary.getMonthsToPayOff() + "");
        ((TextView)findViewById(R.id.originalBalance)).setText(loanPayoutSummary.getOriginalAmountOwed() + "");
        ((TextView)findViewById(R.id.interestAccumulated)).setText(loanPayoutSummary.getInterestAccumulated() + "");
        ((TextView)findViewById(R.id.totalPaid)).setText(loanPayoutSummary.getTotalPaid() + "");
        ((TextView)findViewById(R.id.amountLeft)).setText(loanPayoutSummary.getAmountLeftToPay() + "");
    }

    /**
     * Helper method that will initialize all of the views
     */
    private void initializeViews() {
        this.spinnerYears = (Spinner) findViewById(R.id.spinnerYears);
        this.amountView = (EditText) findViewById(R.id.amount);
        this.interestRateView = (EditText) findViewById(R.id.interestRate);
        this.minPaymentView = (EditText) findViewById(R.id.minPayment);
        this.resultsSpace = findViewById(R.id.tableResults);
    }

    /**
     * Helper method that will add an array adapter to the spinner to represent the year options
     */
    private void fillYearSpinner() {
        //Create the array adapter that will be userd for the spinner
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.yearOptions, android.R.layout.simple_spinner_item);

        //Set the drop down view
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerYears.setAdapter(arrayAdapter);
    }
}
