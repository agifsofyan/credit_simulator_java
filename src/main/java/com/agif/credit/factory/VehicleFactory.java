package com.agif.credit.factory;

import com.agif.credit.enums.VehicleCondition;
import com.agif.credit.enums.VehicleType;
import com.agif.credit.model.Vehicle;

public class VehicleFactory {

    public static Vehicle createVehicle(String typeString, String conditionString, int year) {
        VehicleType type = VehicleType.fromString(typeString);
        VehicleCondition condition = VehicleCondition.fromString(conditionString);
        
        return new Vehicle(type, condition, year);
    }

    public static Vehicle createVehicle(VehicleType type, VehicleCondition condition, int year) {
        return new Vehicle(type, condition, year);
    }
}
