package com.agif.credit.model;

import java.util.List;

public class LoanResult {
    private final Loan loan;
    private final List<YearlyInstallment> yearlyInstallments;

    public LoanResult(Loan loan, List<YearlyInstallment> yearlyInstallments) {
        this.loan = loan;
        this.yearlyInstallments = yearlyInstallments;
    }

    public Loan getLoan() {
        return loan;
    }

    public List<YearlyInstallment> getYearlyInstallments() {
        return yearlyInstallments;
    }

    public double getAverageMonthlyInstallment() {
        double totalMonthlyPayments = yearlyInstallments.stream()
                .mapToDouble(YearlyInstallment::getYearlyPayment)
                .sum();
        
        int totalMonths = loan.getLoanTenure() * 12;
        
        return totalMonthlyPayments / totalMonths;
    }

    @Override
    public String toString() {
        return "LoanResult{" +
                "loan=" + loan +
                ", yearlyInstallments=" + yearlyInstallments +
                ", averageMonthlyInstallment=" + getAverageMonthlyInstallment() +
                '}';
    }
}
