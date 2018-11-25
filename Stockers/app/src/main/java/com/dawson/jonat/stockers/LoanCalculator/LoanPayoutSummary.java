package com.dawson.jonat.stockers.LoanCalculator;

/**
 * This class represents the summary of paying off
 * your credit card.
 */
public class LoanPayoutSummary {
    private double timeToPayOff;
    private double originalBalance;
    private double interestPaid;
    private double totalPaid;
    private double amountLeftToPay;

    /**
     * Constructor that will initialize all of the fields
     * 
     * @param timeToPayOff
     * @param originalBalance
     * @param interestPaid
     * @param totalPaid
     * @param amountLeftToPay
     */
    public LoanPayoutSummary(double timeToPayOff, double originalBalance, double interestPaid,
                             double totalPaid, double amountLeftToPay) {
        this.timeToPayOff = timeToPayOff;
        this.originalBalance = originalBalance;
        this.interestPaid = interestPaid;
        this.totalPaid = totalPaid;
        this.amountLeftToPay = amountLeftToPay;
    }

    public double getTimeToPayOff() {
        return timeToPayOff;
    }

    public void setTimeToPayOff(double timeToPayOff) {
        this.timeToPayOff = timeToPayOff;
    }

    public double getOriginalBalance() {
        return originalBalance;
    }

    public void setOriginalBalance(double originalBalance) {
        this.originalBalance = originalBalance;
    }

    public double getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(double interestPaid) {
        this.interestPaid = interestPaid;
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
