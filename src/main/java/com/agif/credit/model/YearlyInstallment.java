package com.agif.credit.model;

public class YearlyInstallment {
    private final int year;
    private final double interestRate;
    private final double monthlyInstallment;
    private final double yearlyPayment;

    public YearlyInstallment(int year, double interestRate, double monthlyInstallment, double yearlyPayment) {
        this.year = year;
        this.interestRate = interestRate;
        this.monthlyInstallment = monthlyInstallment;
        this.yearlyPayment = yearlyPayment;
    }

    public int getYear() {
        return year;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getMonthlyInstallment() {
        return monthlyInstallment;
    }

    public double getYearlyPayment() {
        return yearlyPayment;
    }

    @Override
    public String toString() {
        return "YearlyInstallment{" +
                "year=" + year +
                ", interestRate=" + interestRate +
                ", monthlyInstallment=" + monthlyInstallment +
                ", yearlyPayment=" + yearlyPayment +
                '}';
    }
}
