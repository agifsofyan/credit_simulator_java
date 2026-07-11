package com.agif.credit.model;

import com.agif.credit.enums.VehicleType;
import com.agif.credit.enums.VehicleCondition;

public class Vehicle {
    private final VehicleType type;
    private final VehicleCondition condition;
    private final int year;

    public Vehicle(VehicleType type, VehicleCondition condition, int year) {
        this.type = type;
        this.condition = condition;
        this.year = year;
    }

    public VehicleType getType() {
        return type;
    }

    public VehicleCondition getCondition() {
        return condition;
    }

    public int getYear() {
        return year;
    }

    public double getBaseInterestRate() {
        return type.getBaseInterestRate();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "type=" + type +
                ", condition=" + condition +
                ", year=" + year +
                '}';
    }
}
