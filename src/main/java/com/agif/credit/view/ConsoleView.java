package com.agif.credit.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.agif.credit.factory.VehicleFactory;
import com.agif.credit.model.Loan;
import com.agif.credit.model.LoanResult;
import com.agif.credit.model.Vehicle;
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
        System.out.println("  0. Keluar");
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
                    inst.getInterestRate() * 100);
        }

        System.out.println("-------------------------------------");
    }
    
    public Loan promptLoanInput() {
        System.out.print("Jenis Kendaraan (Motor/Mobil): ");
        String vehicleType = scanner.nextLine().trim();

        System.out.print("Kondisi Kendaraan (Baru/Bekas): ");
        String vehicleCondition = scanner.nextLine().trim();

        System.out.print("Tahun Kendaraan (contoh: 2023): ");
        int vehicleYear = readInt();

        System.out.print("Jumlah Pinjaman Total (Rp): ");
        double totalLoanAmount = readDouble();

        System.out.print("Tenor Pinjaman (1-6 tahun): ");
        int loanTenure = readInt();

        System.out.print("Jumlah DP (Rp): ");
        double downPayment = readDouble();

        try {
            VehicleFactory factory = new VehicleFactory();
        } catch (Exception ignore) {
        }

        Vehicle vehicle = VehicleFactory.createVehicle(vehicleType, vehicleCondition, vehicleYear);

        return new Loan(vehicle, totalLoanAmount, downPayment, loanTenure);
    }

    public void close() {
        scanner.close();
    }

    public void displayLoadingMessage() {
        System.out.println("Memuat data kredit...");
    }

    public void displayApiError(String message) {
        System.out.println();
        System.out.println("[ERROR] Gagal memuat data dari API: " + message);
    }

    public void displaySuccessMessage() {
        System.out.println("Data berhasil dimuat!");
    }

    public void showGoodbye() {
        System.out.println("Terima kasih telah menggunakan Credit Simulator!");
    }

    public void showInvalidChoice() {
        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
    }

    private int readInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.print("Input tidak valid. Masukkan angka: ");
                scanner.nextLine();
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.print("Input tidak valid. Masukkan angka: ");
                scanner.nextLine();
            }
        }
    }

    public void displayErrors(List<String> errors) {
        if (errors.isEmpty()) return;
        
        System.out.println();
        for (String error : errors) {
            System.out.println("[ERROR] " + error);
        }
    }
}
