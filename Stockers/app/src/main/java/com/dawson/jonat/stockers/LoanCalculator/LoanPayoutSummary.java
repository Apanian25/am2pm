package com.dawson.jonat.stockers.LoanCalculator;

/**
 * This class represents the summary of paying off
 * your credit card.
 */
public class LoanPayoutSummary {
    private int monthsToPayOff;
    private double originalAmountOwed;
    private double interestAccumulated;
    private double totalPaid;
    private double amountLeftToPay;

    /**
     * Constructor that will initialize all of the fields
     *
     * @param monthsToPayOff
     * @param originalAmountOwed
     * @param interestAccumulated
     * @param totalPaid
     * @param amountLeftToPay
     */
    public LoanPayoutSummary(int monthsToPayOff, double originalAmountOwed, double interestAccumulated,
                             double totalPaid, double amountLeftToPay) {
        this.monthsToPayOff = monthsToPayOff;
        this.originalAmountOwed = originalAmountOwed;
        this.interestAccumulated = interestAccumulated;
        this.totalPaid = totalPaid;
        this.amountLeftToPay = amountLeftToPay;
    }

    public int getMonthsToPayOff() { return monthsToPayOff; }

    public void setMonthsToPayOff (int monthsToPayOff) {
        this.monthsToPayOff = monthsToPayOff;
    }

    public double getOriginalAmountOwed() {
        return originalAmountOwed;
    }

    public void setOriginalAmountOwed(double originalAmountOwed) {
        this.originalAmountOwed = originalAmountOwed;
    }

    public double getInterestAccumulated() {
        return interestAccumulated;
    }

    public void setInterestAccumulated(double interestAccumulated) {
        this.interestAccumulated = interestAccumulated;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public double getAmountLeftToPay() {
        return amountLeftToPay;
    }

    public void setAmountLeftToPay(double amountLeftToPay) {
        this.amountLeftToPay = amountLeftToPay;
    }
}
