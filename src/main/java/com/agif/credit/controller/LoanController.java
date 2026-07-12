package com.agif.credit.controller;

import com.agif.credit.model.Loan;
import com.agif.credit.model.LoanResult;
import com.agif.credit.service.LoanCalculationService;
import com.agif.credit.validator.LoanValidator;
import com.agif.credit.view.ConsoleView;

import java.util.List;

public class LoanController {
    private final LoanValidator validator;
    private final LoanCalculationService calculationService;
    private final ConsoleView view;

    public LoanController(LoanValidator validator, LoanCalculationService calculationService, ConsoleView view) {
        this.validator = validator;
        this.calculationService = calculationService;
        this.view = view;
    }

    public void processLoan(Loan loan) {
        List<String> errors = validator.validate(loan);

        LoanResult result = calculationService.calculate(loan);
        view.displayResult(result);
    }
}
