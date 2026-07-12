package com.agif.credit.enums;

public enum VehicleType {
    MOBIL(0.08, "Mobil"),
    MOTOR(0.09, "Motor");

    private final double baseInterestRate;
    private final String label;

    VehicleType(double baseInterestRate, String label) {
        this.baseInterestRate = baseInterestRate;
        this.label = label;
    }

    public double getBaseInterestRate() {
        return baseInterestRate;
    }

    public static VehicleType fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null");
        }

        String normalized = value.trim().toUpperCase();

        try {
            return VehicleType.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid vehicle type: " + value + ". Valid options: MOBIL, MOTOR");
        }
    }
    
    public String getLabel() {
        return label;
    }
}
