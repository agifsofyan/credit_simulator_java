package com.agif.credit;

import java.io.IOException;

import com.agif.credit.controller.LoanController;
import com.agif.credit.model.Loan;
import com.agif.credit.service.ApiService;
import com.agif.credit.service.FileService;
import com.agif.credit.service.InterestRateService;
import com.agif.credit.service.LoanCalculationService;
import com.agif.credit.validator.LoanValidator;
import com.agif.credit.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        InterestRateService interestRateService = new InterestRateService();
        LoanCalculationService calculationService = new LoanCalculationService(interestRateService);
        LoanValidator validator = new LoanValidator();
        ConsoleView view = new ConsoleView();
        LoanController controller = new LoanController(validator, calculationService, view);
        ApiService apiService = new ApiService();

        view.showWelcome();

        if (args.length == 1) {
            try {
                Loan loan = FileService.readFromFile(args[0]);
                System.out.println("Data berhasil dimuat dari file: " + args[0]);
            } catch (IOException e) {
                System.out.println("[ERROR] Gagal membaca file: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Format angka tidak valid di file: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        boolean running = true;
        while (running) {
            int choice = view.showMenu();

            switch (choice) {
                case 1:
                    processNewSimulation(controller, view);
                    break;

                case 2:
                    processLoadExisting(controller, view, apiService);
                    break;

                case 0:
                    running = false;
                    view.showGoodbye();
                    break;

                default:
                    view.showInvalidChoice();
            }
        }

        view.close();
    }

    private static void processNewSimulation(LoanController controller, ConsoleView view) {
        try {
            Loan loan = view.promptLoanInput();
            controller.processLoan(loan);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void processLoadExisting(LoanController controller, ConsoleView view, ApiService apiService) {
        try {
            Loan loan = apiService.loadFromApi();
            view.displaySuccessMessage();
            controller.processLoan(loan);
        } catch (IOException | InterruptedException e) {
            view.displayApiError(e.getMessage());
        }
    }
}