package com.dawson.jonat.stockers.LoanCalculator;

import android.content.Context;
import android.widget.Toast;

public class InvalidCalculationValuesException extends Exception {

    public InvalidCalculationValuesException(String msg, Context context) {
        super(msg);
    }
}
