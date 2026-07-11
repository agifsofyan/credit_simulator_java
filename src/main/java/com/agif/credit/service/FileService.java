package com.agif.credit.service;

import com.agif.credit.enums.VehicleCondition;
import com.agif.credit.enums.VehicleType;
import com.agif.credit.model.Loan;
import com.agif.credit.model.Vehicle;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileService {

    public static Loan readFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IOException("File not found: " + filePath);
        }

        List<String> lines = Files.readAllLines(path);

        if (lines.size() < 6) {
            throw new IOException("Invalid file format: expected 6 lines, got " + lines.size());
        }

        String vehicleTypeStr = lines.get(0).trim();
        String vehicleConditionStr = lines.get(1).trim();
        int vehicleYear = Integer.parseInt(lines.get(2).trim());
        double totalLoanAmount = Double.parseDouble(lines.get(3).trim());
        int loanTenure = Integer.parseInt(lines.get(4).trim());
        double downPayment = Double.parseDouble(lines.get(5).trim());

        VehicleType type = VehicleType.fromString(vehicleTypeStr);
        VehicleCondition condition = VehicleCondition.fromString(vehicleConditionStr);
        
        Vehicle vehicle = new Vehicle(type, condition, vehicleYear);

        return new Loan(vehicle, totalLoanAmount, downPayment, loanTenure);
    }
}
