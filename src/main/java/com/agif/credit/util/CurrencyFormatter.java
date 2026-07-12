package com.agif.credit.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CurrencyFormatter {

    private static final DecimalFormat formatter;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        
        formatter = new DecimalFormat("#.##0", symbols);
    }

    public static String format(double amount) {
        return "Rp. " + formatter.format(amount);
    }
}
