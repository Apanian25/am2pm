package com.dawson.jonat.stockers.LoanCalculator;

public class CalculationInformation {

    private double principal;
    private double interestRate;
    private double minimumPayment;
    private int years;

    /**
     * Set all of the information needed in order to make a Loan Calculation
     * @param principal
     * @param interestRate
     * @param minimumPayment
     * @param years
     */
    public CalculationInformation(double principal, double interestRate, double minimumPayment, int years) {
        this.setPrincipal(principal);
        this.setInterestRate(interestRate);
        this.setMinimumPayment(minimumPayment);
        this.setYears(years);
    }


    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getMinimumPayment() {
        return minimumPayment;
    }

    public void setMinimumPayment(double minimumPayment) {
        this.minimumPayment = minimumPayment;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }
}
