package com.agif.credit.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {

    private static final String localLanguage = "id";
    private static final String localCountry = "ID";

    private static DecimalFormat setCurrency() {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(new Locale(localLanguage, localCountry));
        formatter.applyPattern("#,###");

        return formatter;
    }
    
    /**
     * Format double to Indonesia Currency
     * @param amount
     * @return
     */
    public static String format(double amount) {
        return "Rp " + setCurrency().format(amount);
    }
}
