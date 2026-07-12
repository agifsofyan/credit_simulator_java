package com.agif.credit.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.agif.credit.model.Loan;
import com.agif.credit.model.LoanResult;
import com.agif.credit.model.YearlyInstallment;

public class ConsoleView {
    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("   VEHICLE CREDIT / LOAN SIMULATOR");
        System.out.println("==========================================");
        System.out.println();
    }

    public int showMenu() {
        System.out.println("Pilih menu:");
        System.out.println("  1. Simulasi Kredit Baru");
        System.out.println("  2. Load Existing Calculation");
        System.out.print("Pilihan Anda: ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }

    public void displayResult(LoanResult result) {
        Loan loan = result.getLoan();

        System.out.println();
        System.out.println("---------- HASIL SIMULASI ----------");
        System.out.println("Jenis Kendaraan   : " + loan.getVehicle().getType());
        System.out.println("Kondisi Kendaraan : " + loan.getVehicle().getCondition());
        System.out.println("Tahun Kendaraan   : " + loan.getVehicle().getYear());
        System.out.println("Pinjaman Total    : " + loan.getTotalLoanAmount());
        System.out.println("DP                : " + loan.getDownPayment());
        System.out.println("Pokok Pinjaman    : " + loan.getPrincipalAmount());
        System.out.println("Tenor             : " + loan.getLoanTenure() + " tahun");
        System.out.println();
        System.out.println("Cicilan Per Bulan Berdasarkan Tahun Berjalan:");

        for (YearlyInstallment inst : result.getYearlyInstallments()) {
            System.out.printf("  Tahun %d : %s/bln , Suku Bunga : %.1f%%%n",
                    inst.getYear(),
                    inst.getMonthlyInstallment(),
                    inst.getInterestRate() * 100
            );
        }

        System.out.println("-------------------------------------");
    }
}
