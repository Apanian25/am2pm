package com.dawson.jonat.stockers.LoanCalculator;

public class LoanCalculator {
    private static final double AVERAGE_DAYS_PER_MONTH = 30.4169;

    /**
     * Generates a Loan Payout Summary based on the amount is owed, the interest rate, the minPayment amount
     * and on the amount of years in the future to see.
     *
     * @param debt
     * @param rate
     * @param minPayment
     * @param years
     * @return
     */
    public static LoanPayoutSummary calculateInterestPayoutSummary(double debt, double rate, double minPayment, int years) {
        double amountPaid = 0;
        double interestAccumulated = 0;
        double amountLeftToPay = debt;
        double dailyRate = (rate / 100) / 365;
        int monthsPaid = 0;
        int monthsToCalculate = years * 12;

        while (monthsPaid < monthsToCalculate && amountLeftToPay > 0) {
            //First we pay off the min payment (As you have your grace period)
            amountLeftToPay -= minPayment;
            amountPaid += minPayment;

            //Check if there is still some money left to pay (which will generate more interest)
            if (amountLeftToPay > 0) {
                double interest = calculateInterestForAMonth(amountLeftToPay, dailyRate);
                interestAccumulated += interest;
                amountLeftToPay += interest;
            } else {
                //If we reach here it means that we may have paid more than what we needed to, so if
                //that is the case then we need remove the extra amount we paid from what we have paid
                amountPaid += amountLeftToPay;
                amountLeftToPay = 0;
            }
            monthsPaid++;
        }

        return new LoanPayoutSummary(monthsPaid, debt, interestAccumulated, amountPaid, amountLeftToPay);
    }

    /**
     * Calculates the amount of interest that is owed per month.
     *
     * @param debt
     * @param ratePerDay
     * @return
     */
    private static double calculateInterestForAMonth(double debt, double ratePerDay) {
        return debt * Math.pow(1 + ratePerDay, AVERAGE_DAYS_PER_MONTH) - debt;
    }

}
