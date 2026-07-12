package com.agif.credit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterestRateServiceTest {

    private InterestRateService service;

    @BeforeEach
    void setUp() {
        service = new InterestRateService();
    }

    @Test
    void testCalculateRatesMobilYear1() {
        List<Double> rates = service.calculateYearlyRates(0.08, 1);
        assertEquals(1, rates.size());
        assertEquals(0.080, rates.get(0), 0.0001);
    }

    @Test
    void testCalculateRatesMobilYear2() {
        List<Double> rates = service.calculateYearlyRates(0.08, 2);
        assertEquals(2, rates.size());
        assertEquals(0.080, rates.get(0), 0.0001);
        assertEquals(0.081, rates.get(1), 0.0001);
    }

    @Test
    void testCalculateRatesMobilYear3() {
        List<Double> rates = service.calculateYearlyRates(0.08, 3);
        assertEquals(3, rates.size());
        assertEquals(0.080, rates.get(0), 0.0001);
        assertEquals(0.081, rates.get(1), 0.0001);
        assertEquals(0.086, rates.get(2), 0.0001);
    }

    @Test
    void testCalculateRatesMobilYear6() {
        List<Double> rates = service.calculateYearlyRates(0.08, 6);
        assertEquals(6, rates.size());
        assertEquals(0.080, rates.get(0), 0.0001);
        assertEquals(0.081, rates.get(1), 0.0001);
        assertEquals(0.086, rates.get(2), 0.0001);
        assertEquals(0.087, rates.get(3), 0.0001);
        assertEquals(0.092, rates.get(4), 0.0001);
        assertEquals(0.093, rates.get(5), 0.0001);
    }

    @Test
    void testCalculateRatesMotor() {
        List<Double> rates = service.calculateYearlyRates(0.09, 3);
        assertEquals(3, rates.size());
        assertEquals(0.090, rates.get(0), 0.0001);
        assertEquals(0.091, rates.get(1), 0.0001);
        assertEquals(0.096, rates.get(2), 0.0001);
    }

    @Test
    void testCalculateRatesMotorYear6() {
        List<Double> rates = service.calculateYearlyRates(0.09, 6);
        assertEquals(6, rates.size());
        assertEquals(0.090, rates.get(0), 0.0001);
        assertEquals(0.091, rates.get(1), 0.0001);
        assertEquals(0.096, rates.get(2), 0.0001);
        assertEquals(0.097, rates.get(3), 0.0001);
        assertEquals(0.102, rates.get(4), 0.0001);
        assertEquals(0.103, rates.get(5), 0.0001);
    }

    @Test
    void testCalculateRates_InvalidTenureBelow1() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.calculateYearlyRates(0.08, 0);
        });
    }

    @Test
    void testCalculateRates_InvalidTenureAbove6() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.calculateYearlyRates(0.08, 7);
        });
    }

    @Test
    void testRatesAreUnmodifiable() {
        List<Double> rates = service.calculateYearlyRates(0.08, 1);
        assertThrows(UnsupportedOperationException.class, () -> {
            rates.add(0.09);
        });
    }
}
