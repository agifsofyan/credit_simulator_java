package com.agif.credit.enums;

public enum VehicleCondition {
    BARU(0.35),
    BEKAS(0.25);

    private final double minimumDownPaymentPercentage;

    VehicleCondition(double minimumDownPaymentPercentage) {
        this.minimumDownPaymentPercentage = minimumDownPaymentPercentage;
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
}
