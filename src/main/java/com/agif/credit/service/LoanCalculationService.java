package com.agif.credit.service;

import com.agif.credit.model.Loan;
import com.agif.credit.model.LoanResult;
import com.agif.credit.model.YearlyInstallment;

import java.util.ArrayList;
import java.util.List;

public class LoanCalculationService {

    private final InterestRateService interestRateService;

    public LoanCalculationService(InterestRateService interestRateService) {
        this.interestRateService = interestRateService;
    }

    public LoanResult calculate(Loan loan) {
        List<Double> rates = interestRateService.calculateYearlyRates(
                loan.getVehicle().getBaseInterestRate(),
                loan.getLoanTenure()
        );

        List<YearlyInstallment> installments = new ArrayList<>();
        double principalRemaining = loan.getPrincipalAmount();

        for (int year = 1; year <= loan.getLoanTenure(); year++) {
            double rate = rates.get(year - 1);

            double totalWithInterest = principalRemaining * (1.0 + rate);
            int monthsRemaining = (loan.getLoanTenure() * 12) - ((year - 1) * 12);
            double monthlyInstallment = totalWithInterest / monthsRemaining;
            double yearlyPayment = monthlyInstallment * 12;

            installments.add(new YearlyInstallment(year, rate, monthlyInstallment, yearlyPayment));

            principalRemaining = totalWithInterest - yearlyPayment;
        }

        return new LoanResult(loan, installments);
    }
}
