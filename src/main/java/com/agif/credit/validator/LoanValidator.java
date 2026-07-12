package com.agif.credit.validator;

import com.agif.credit.enums.VehicleCondition;
import com.agif.credit.model.Loan;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class LoanValidator {

    public List<String> validate(Loan loan) {
        List<String> errors = new ArrayList<>();
        int currentYear = Year.now().getValue();

        if (loan.getVehicle().getCondition() == VehicleCondition.BARU) {
            int minYear = currentYear - 1;
            if (loan.getVehicle().getYear() < minYear) {
                errors.add("Tahun kendaraan Baru tidak boleh kurang dari " + minYear);
            }
        }

        if (loan.getLoanTenure() < 1 || loan.getLoanTenure() > 6) {
            errors.add("Tenor pinjaman harus antara 1-6 tahun");
        }

        if (loan.getTotalLoanAmount() > 1_000_000_000) {
            errors.add("Jumlah pinjaman total tidak boleh lebih dari Rp. 1,000,000,000.00");
        }

        if (loan.getTotalLoanAmount() <= 0) {
            errors.add("Jumlah pinjaman total harus lebih dari 0");
        }

        double minDP = loan.getTotalLoanAmount() *
                loan.getVehicle().getCondition().getMinimumDownPaymentPercentage();

        if (loan.getDownPayment() < minDP) {
            errors.add(String.format(
                    "Jumlah DP untuk kendaraan %s harus minimal %.0f%% dari jumlah pinjaman total",
                    getDisplayCondition(loan.getVehicle().getCondition()),
                    loan.getVehicle().getCondition().getMinimumDownPaymentPercentage() * 100
            ));
        }

        if (loan.getDownPayment() >= loan.getTotalLoanAmount()) {
            errors.add("DP harus kurang dari jumlah pinjaman total");
        }

        if (loan.getDownPayment() < 0) {
            errors.add("DP tidak boleh negatif");
        }

        return errors;
    }

    private String getDisplayCondition(VehicleCondition condition) {
        switch (condition) {
            case BARU:
                return "Baru";
            case BEKAS:
                return "Bekas";
            default:
                return condition.name();
        }
    }
}
