# Vehicle Credit Simulator

A Java console application to calculate monthly installments for vehicle loans.

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+**

## Build

```bash
mvn clean package
```

This will create `target/credit-simulator.jar` (fat JAR with dependencies).

## Run

### Interactive Mode

```bash
./bin/credit_simulator
```

### File Input Mode

```bash
./bin/credit_simulator file_input.txt
```

### Using Java Directly

```bash
java -jar target/credit-simulator.jar
java -jar target/credit-simulator.jar file_input.txt
```

## Run Tests

```bash
mvn test
```

## Features

1. **Interactive Console Mode** - Menu-driven input with looping
2. **File Input Mode** - Read data from plain text file
3. **API Integration** - Load existing calculation from Mocky.io
4. **Input Validation** - All rules enforced (year, tenor, DP, loan amount)
5. **Interest Rate Calculation** - Based on Excel formula (verified)

## Interest Rate Formula

Base rates:
- **Mobil**: 8%
- **Motor**: 9%

Yearly increments:
- **+0.1%** every year
- **+0.5%** every 2 years (bonus at year 3, 5)

Example for Mobil (base 8%):
- Year 1: 8.0%
- Year 2: 8.1%
- Year 3: 8.6%
- Year 4: 8.7%
- Year 5: 9.2%
- Year 6: 9.3%

## Design Patterns

- **Factory Pattern**: VehicleFactory for creating vehicles
- **MVC Pattern**: Model-View-Controller separation
- **Strategy Pattern**: Interest rate calculation strategy

## File Input Format

Plain text file, 6 lines:

```
JenisKendaraan
KondisiKendaraan
TahunKendaraan
JumlahPinjaman
Tenor
DP
```

Example (`file_input.txt`):
```
Mobil
Baru
2026
40000000
5
15000000
```
