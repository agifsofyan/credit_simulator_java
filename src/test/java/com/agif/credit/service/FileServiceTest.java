package com.agif.credit.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    @Test
    void testReadFromFile() throws IOException {
        Path tempFile = Files.createTempFile("credit_test", ".txt");
        Files.writeString(tempFile,
                "Mobil\n" +
                "Baru\n" +
                "2026\n" +
                "40000000\n" +
                "5\n" +
                "15000000\n");

        var loan = FileService.readFromFile(tempFile.toString());

        assertEquals("MOBIL", loan.getVehicle().getType().name());
        assertEquals("BARU", loan.getVehicle().getCondition().name());
        assertEquals(2026, loan.getVehicle().getYear());
        assertEquals(40_000_000, loan.getTotalLoanAmount(), 0.01);
        assertEquals(5, loan.getLoanTenure());
        assertEquals(15_000_000, loan.getDownPayment(), 0.01);
        assertEquals(25_000_000, loan.getPrincipalAmount(), 0.01);

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testReadFromFileMotorBekas() throws IOException {
        Path tempFile = Files.createTempFile("credit_test", ".txt");
        Files.writeString(tempFile,
                "Motor\n" +
                "Bekas\n" +
                "2020\n" +
                "20000000\n" +
                "2\n" +
                "6000000\n");

        var loan = FileService.readFromFile(tempFile.toString());

        assertEquals("MOTOR", loan.getVehicle().getType().name());
        assertEquals("BEKAS", loan.getVehicle().getCondition().name());
        assertEquals(2020, loan.getVehicle().getYear());

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testFileNotFound() {
        assertThrows(IOException.class, () -> {
            FileService.readFromFile("/nonexistent/file.txt");
        });
    }

    @Test
    void testInvalidFileFormat() {
        assertThrows(IOException.class, () -> {
            Path tempFile = Files.createTempFile("credit_test", ".txt");
            Files.writeString(tempFile, "only\none\nline\n");
            
            FileService.readFromFile(tempFile.toString());
            
            Files.deleteIfExists(tempFile);
        });
    }

    @Test
    void testInvalidNumericValue() {
        assertThrows(NumberFormatException.class, () -> {
            Path tempFile = Files.createTempFile("credit_test", ".txt");
            Files.writeString(tempFile,
                    "Mobil\n" +
                    "Baru\n" +
                    "invalid_year\n" +
                    "40000000\n" +
                    "5\n" +
                    "15000000\n");

            FileService.readFromFile(tempFile.toString());

            Files.deleteIfExists(tempFile);
        });
    }
}
