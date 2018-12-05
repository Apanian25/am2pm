package com.dawson.jonat.stockers.LoanCalculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dawson.jonat.stockers.ContactDialog.ContactUtilities;
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

        if (savedInstanceState != null) {
            LoanPayoutSummary summary = new LoanPayoutSummary(savedInstanceState.getInt("timeToPayOff"),
                    savedInstanceState.getDouble("ogBalance"), savedInstanceState.getDouble("interestAccumulated"),
                    savedInstanceState.getDouble("totalAmountPaid"), savedInstanceState.getDouble("amountLeft"));
            displayResults(summary);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        LoanPayoutSummary summary = (LoanPayoutSummary)resultsTable.getTag();
        savedInstanceState.putDouble("ogBalance", summary.getOriginalAmountOwed());
        savedInstanceState.putDouble("amoutLeft", summary.getAmountLeftToPay());
        savedInstanceState.putDouble("interestAccumulated", summary.getInterestAccumulated());
        savedInstanceState.putDouble("totalAmountPaid", summary.getTotalPaid());
        savedInstanceState.putInt("timeToPayOff", summary.getMonthsToPayOff());
    }

    /**
     * Reads the user input and will then calculate the LoanPaymentResults, which will then be
     * displayed on the screen. If there are any errors with the data, a toast message will be
     * displayed
     *
     * @param view
     */
    public void calculateAndDisplayResults(View view) {
        try {
            CalculationInformation info = this.validateAndRetrieveValues();
            this.displayResults(LoanCalculator.calculateInterestPayoutSummary(info.getPrincipal(),
                   info.getInterestRate(), info.getMinimumPayment(), info.getYears()));
        } catch (InvalidCalculationValuesException exception) {
            this.displayErrorToastMessage(exception.getMessage());
        }
    }



    /**
     * Event handler that will check if results have been calculated, and if they have, it will then
     * launch the contact list for the user to choose who to send the results to, if no results have
     * been calculated, a meaningful message will be displayed on screen.
     *
     * @param view
     */
    public void displayContacts(View view) {
        if (resultsTable == null) {
            Toast.makeText(this, "First calculate a result before sending an email to friends", Toast.LENGTH_LONG).show();
        } else {
            ContactUtilities.launchDialogToSendEmail(this, (LoanPayoutSummary)resultsTable.getTag());
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
        resultsTable.setTag(loanPayoutSummary);
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

    /**
     * Helper method that will display a toast message with a specified string of text
     */
    private void displayErrorToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * This method will test to ensure that all of the information entered by the user is valid. If
     * the infromation is valid, then return the information in a CalculationInformation calss and if
     * the information is invalid, a InvalidCalculationValuesException will be thrown with a message
     * saying which field is invalid.
     *
     * @return
     * @throws InvalidCalculationValuesException
     */
    private CalculationInformation validateAndRetrieveValues() throws InvalidCalculationValuesException {
        double amount = 0;
        double interestRate = 0;
        double minPayment = 0;
        int years = Integer.parseInt(spinnerYears.getSelectedItem().toString());

        //The string value checked is used to keep track of what was the field that caused the exception
        //to be thrown, in order to tell the user which field has the error
        String valueChecked = "amount";

        try {
            amount = Double.parseDouble(this.amountView.getText().toString());

            valueChecked = "interest rate";
            interestRate = Double.parseDouble(this.interestRateView.getText().toString());

            valueChecked = "minimum payment";
            minPayment = Double.parseDouble(this.minPaymentView.getText().toString());
        } catch (NumberFormatException exception) {
            throw new InvalidCalculationValuesException("Opps, looks like the value you placed in " + valueChecked
                    + " is not valid. Please change that value.", this);
        }

        return new CalculationInformation(amount, interestRate, minPayment, years);
    }
}
