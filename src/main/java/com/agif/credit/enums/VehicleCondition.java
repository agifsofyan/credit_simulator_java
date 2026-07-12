package com.agif.credit.enums;

public enum VehicleCondition {
    BARU(0.35, "Baru"),
    BEKAS(0.25, "Bekas");

    private final double minimumDownPaymentPercentage;
    private final String label;

    VehicleCondition(double minimumDownPaymentPercentage, String label) {
        this.minimumDownPaymentPercentage = minimumDownPaymentPercentage;
        this.label = label;
    }

    public double getMinimumDownPaymentPercentage() {
        return minimumDownPaymentPercentage;
    }

    public static VehicleCondition fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Vehicle condition cannot be null");
        }

        String normalized = value.trim().toUpperCase();

        try {
            return VehicleCondition.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid vehicle condition: " + value + ". Valid options: BARU, BEKAS");
        }
    }
    
    public String getLabel() {
        return label;
    }
}
