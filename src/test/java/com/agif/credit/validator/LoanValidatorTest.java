package com.agif.credit.validator;

import com.agif.credit.enums.VehicleCondition;
import com.agif.credit.enums.VehicleType;
import com.agif.credit.model.Loan;
import com.agif.credit.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanValidatorTest {

    private LoanValidator validator;

    @BeforeEach
    void setUp() {
        validator = new LoanValidator();
    }

    @Test
    void testValidLoanNew() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 40_000_000, 15_000_000, 5);

        List<String> errors = validator.validate(loan);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testValidLoanSecond() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BEKAS, 2020);
        Loan loan = new Loan(vehicle, 100_000_000, 25_000_000, 3);

        List<String> errors = validator.validate(loan);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testInvalidYearForNewVehicle() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2022);
        Loan loan = new Loan(vehicle, 50_000_000, 20_000_000, 3);

        List<String> errors = validator.validate(loan);
        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(e -> e.contains("Tahun") && e.contains("Baru")));
    }

    @Test
    void testTenorTooHigh() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 50_000_000, 20_000_000, 7);

        List<String> errors = validator.validate(loan);
        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(e -> e.contains("Tenor")));
    }

    @Test
    void testTenorTooLow() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 50_000_000, 20_000_000, 0);

        List<String> errors = validator.validate(loan);
        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(e -> e.contains("Tenor")));
    }

    @Test
    void testTotalLoanExceedsMax() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 2_000_000_000, 700_000_000, 3);

        List<String> errors = validator.validate(loan);
        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(e -> e.contains("1,000,000,000")));
    }

    @Test
    void testTotalLoanNegative() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, -100_000, 0, 3);

        List<String> errors = validator.validate(loan);
        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(e -> e.contains("lebih dari 0")));
    }

    @Test
    void testDPNewBelowMinimum() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 100_000_000, 30_000_000, 3);

        List<String> errors = validator.validate(loan);
        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(e -> e.contains("DP")));
    }

    @Test
    void testDPNewMinimalPass() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 100_000_000, 35_000_000, 3);

        List<String> errors = validator.validate(loan);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testDPSecondBelowMinimum() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BEKAS, 2020);
        Loan loan = new Loan(vehicle, 100_000_000, 20_000_000, 3);

        List<String> errors = validator.validate(loan);
        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(e -> e.contains("DP")));
    }

    @Test
    void testDPSecondMinimalPass() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BEKAS, 2020);
        Loan loan = new Loan(vehicle, 100_000_000, 25_000_000, 3);

        List<String> errors = validator.validate(loan);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testDPExceedsTotalLoan() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 100_000_000, 100_000_000, 3);

        List<String> errors = validator.validate(loan);
        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(e -> e.contains("DP harus kurang")));
    }

    @Test
    void testDPEqualsTotalLoan() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 100_000_000, 100_000_001, 3);

        List<String> errors = validator.validate(loan);
        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(e -> e.contains("DP harus kurang")));
    }

    @Test
    void testDPNegative() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 100_000_000, -10_000, 3);

        List<String> errors = validator.validate(loan);
        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(e -> e.contains("DP tidak boleh negatif")));
    }

    @Test
    void testMultipleErrors() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2020);
        Loan loan = new Loan(vehicle, 2_000_000_000, 10_000_000, 7);

        List<String> errors = validator.validate(loan);
        assertTrue(errors.size() >= 3);
    }

    @Test
    void testMotorNew() {
        Vehicle vehicle = new Vehicle(VehicleType.MOTOR, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 20_000_000, 7_000_000, 2);

        List<String> errors = validator.validate(loan);
        assertTrue(errors.isEmpty());
    }

    @Test
    void testZeroLoanAmount() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 0, 0, 3);

        List<String> errors = validator.validate(loan);
        assertFalse(errors.isEmpty());
    }
}
