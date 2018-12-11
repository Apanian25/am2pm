package com.dawson.jonat.stockers.LoanCalculator;

import android.content.Context;
import android.widget.Toast;

/**
 * Exception that represents that the current values for the calculations are invalid
 */
public class InvalidCalculationValuesException extends Exception {

    public InvalidCalculationValuesException(String msg, Context context) {
        super(msg);
    }
}
