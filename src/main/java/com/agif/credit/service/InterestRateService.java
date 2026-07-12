package com.agif.credit.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InterestRateService {

    public List<Double> calculateYearlyRates(double baseRate, int tenure) {
        if (tenure < 1 || tenure > 6) {
            throw new IllegalArgumentException("Tenure must be between 1 and 6 years");
        }

        List<Double> rates = new ArrayList<>();

        for (int year = 1; year <= tenure; year++) {
            int cycleYear = (year - 1) % 2;
            int fullCycles = (year - 1) / 2;

            double annualIncrement = 0.001 * (cycleYear == 0 ? fullCycles : fullCycles + 1);
            double biennialBonus = 0.005 * fullCycles;

            double rate = baseRate + annualIncrement + biennialBonus;
            rates.add(rate);
        }

        return Collections.unmodifiableList(rates);
    }
}
