package com.agif.credit;

import java.io.IOException;

import com.agif.credit.model.Loan;
import com.agif.credit.service.FileService;
import com.agif.credit.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();

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
    }
}