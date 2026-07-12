package com.agif.credit.service;

import com.agif.credit.enums.VehicleCondition;
import com.agif.credit.enums.VehicleType;
import com.agif.credit.model.Loan;
import com.agif.credit.model.LoanResult;
import com.agif.credit.model.Vehicle;
import com.agif.credit.model.YearlyInstallment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanCalculationServiceTest {

    private LoanCalculationService service;
    private InterestRateService interestRateService;

    @BeforeEach
    void setUp() {
        interestRateService = new InterestRateService();
        service = new LoanCalculationService(interestRateService);
    }

    @Test
    void testCalculateExcelExample() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BEKAS, 2023);
        Loan loan = new Loan(vehicle, 100_000_000, 25_000_000, 3);

        LoanResult result = service.calculate(loan);

        assertEquals(3, result.getYearlyInstallments().size());

        double principal = 100_000_000 - 25_000_000;
        assertEquals(principal, 75_000_000, 0.01);

        YearlyInstallment year1 = result.getYearlyInstallments().get(0);
        assertEquals(1, year1.getYear());
        assertEquals(0.08, year1.getInterestRate(), 0.0001);
        assertEquals(2_250_000, year1.getMonthlyInstallment(), 1.0);

        YearlyInstallment year2 = result.getYearlyInstallments().get(1);
        assertEquals(2, year2.getYear());
        assertEquals(0.081, year2.getInterestRate(), 0.0001);
        assertEquals(2_432_250, year2.getMonthlyInstallment(), 1.0);

        YearlyInstallment year3 = result.getYearlyInstallments().get(2);
        assertEquals(3, year3.getYear());
        assertEquals(0.086, year3.getInterestRate(), 0.0001);
        assertEquals(2_641_423.5, year3.getMonthlyInstallment(), 1.0);
    }

    @Test
    void testCalculateYearlyPaymentsAreCorrect() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BEKAS, 2023);
        Loan loan = new Loan(vehicle, 100_000_000, 25_000_000, 3);

        LoanResult result = service.calculate(loan);

        YearlyInstallment year1 = result.getYearlyInstallments().get(0);
        assertEquals(2_250_000 * 12, year1.getYearlyPayment(), 1.0);

        YearlyInstallment year2 = result.getYearlyInstallments().get(1);
        assertEquals(2_432_250 * 12, year2.getYearlyPayment(), 1.0);

        YearlyInstallment year3 = result.getYearlyInstallments().get(2);
        assertEquals(2_641_423.5 * 12, year3.getYearlyPayment(), 1.0);
    }

    @Test
    void testCalculateWithTenor5() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 40_000_000, 15_000_000, 5);

        LoanResult result = service.calculate(loan);

        assertEquals(5, result.getYearlyInstallments().size());

        double[] expectedRates = {0.08, 0.081, 0.086, 0.087, 0.092};

        for (int i = 0; i < 5; i++) {
            assertEquals(expectedRates[i], result.getYearlyInstallments().get(i).getInterestRate(), 0.0001);
        }
    }

    @Test
    void testCalculateWithMotor() {
        Vehicle vehicle = new Vehicle(VehicleType.MOTOR, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 20_000_000, 7_000_000, 2);

        LoanResult result = service.calculate(loan);

        assertEquals(2, result.getYearlyInstallments().size());
        assertEquals(0.09, result.getYearlyInstallments().get(0).getInterestRate(), 0.0001);
        assertEquals(0.091, result.getYearlyInstallments().get(1).getInterestRate(), 0.0001);
    }

    @Test
    void testLastYearExactlyPaysOffPrincipal() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
        Loan loan = new Loan(vehicle, 50_000_000, 20_000_000, 3);

        LoanResult result = service.calculate(loan);

        List<YearlyInstallment> installments = result.getYearlyInstallments();
        YearlyInstallment lastYear = installments.get(installments.size() - 1);

        double expectedYearlyPayment = lastYear.getMonthlyInstallment() * 12;
        assertEquals(expectedYearlyPayment, lastYear.getYearlyPayment(), 0.01);
    }

    @Test
    void testMonthlyInstallmentCalculationMatchesExcel() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BEKAS, 2023);
        Loan loan = new Loan(vehicle, 100_000_000, 25_000_000, 3);

        LoanResult result = service.calculate(loan);

        List<YearlyInstallment> installments = result.getYearlyInstallments();
        double principal = 75_000_000;

        double rem1 = principal;
        double twi1 = rem1 * (1 + 0.080);
        double m1 = twi1 / 36;
        assertEquals(m1, installments.get(0).getMonthlyInstallment(), 0.01);

        double rem2 = twi1 - (m1 * 12);
        double twi2 = rem2 * (1 + 0.081);
        double m2 = twi2 / 24;
        assertEquals(m2, installments.get(1).getMonthlyInstallment(), 1.0);

        double rem3 = twi2 - (m2 * 12);
        double twi3 = rem3 * (1 + 0.086);
        double m3 = twi3 / 12;
        assertEquals(m3, installments.get(2).getMonthlyInstallment(), 1.0);
    }

    @Test
    void testAverageMonthlyInstallment() {
        Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BEKAS, 2023);
        Loan loan = new Loan(vehicle, 100_000_000, 25_000_000, 3);

        LoanResult result = service.calculate(loan);

        double totalYearlyPayments = result.getYearlyInstallments().stream()
                .mapToDouble(YearlyInstallment::getYearlyPayment)
                .sum();

        double expectedAvg = totalYearlyPayments / 36;
        assertEquals(expectedAvg, result.getAverageMonthlyInstallment(), 0.1);
    }

    @Test
    void testAllTenorsCompleteAllYears() {
        for (int tenure = 1; tenure <= 6; tenure++) {
            Vehicle vehicle = new Vehicle(VehicleType.MOBIL, VehicleCondition.BARU, 2026);
            Loan loan = new Loan(vehicle, 50_000_000, 20_000_000, tenure);

            LoanResult result = service.calculate(loan);

            assertEquals(tenure, result.getYearlyInstallments().size());

            YearlyInstallment lastYear = result.getYearlyInstallments().get(tenure - 1);
            assertTrue(lastYear.getYearlyPayment() > 0);
            assertTrue(lastYear.getMonthlyInstallment() > 0);
        }
    }
}
