package com.agif.credit.enums;

public enum VehicleType {
    MOBIL(0.08),
    MOTOR(0.09);

    private final double baseInterestRate;

    VehicleType(double baseInterestRate) {
        this.baseInterestRate = baseInterestRate;
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
}
