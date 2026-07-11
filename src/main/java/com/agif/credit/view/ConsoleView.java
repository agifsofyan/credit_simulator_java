package com.agif.credit.view;

import java.util.InputMismatchException;
import java.util.Scanner;

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
}
