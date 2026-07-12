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
./bin/credit-simulator
```

### File Input Mode

```bash
./bin/credit-simulator file_input.txt
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

<br><br>

## Build and Run via Docker

### Build Docker using Docker Compose

```bash
docker-compose up -d
```

### Attach Docker to run loan menu

```bash
docker attach credit-simulator
```

### Interactive Mode

```bash
./bin/credit-simulator
```

### File Input Mode

```bash
./bin/credit-simulator file_input.txt
```

### Run using Java (Jar)

```bash
java -jar target/credit-simulator.jar
```

### Run docker again
Docker will shut down when the program is finished using it
```bash
docker-compose start
```

<br><br>


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

## Project Structure

```
src/
├── main/java/com/agif/credit/
│   ├── enums/         # VehicleType, VehicleCondition
│   ├── model/         # Vehicle, Loan, YearlyInstallment, LoanResult
│   ├── factory/       # VehicleFactory
│   ├── service/       # InterestRateService, LoanCalculationService, FileService, ApiService
│   ├── validator/     # LoanValidator
│   ├── controller/    # LoanController (MVC)
│   ├── view/          # ConsoleView
│   ├── util/          # CurrencyFormatter
│   └── Main.java
└── test/java/com/agif/credit/
    ├── service/       # InterestRateServiceTest, LoanCalculationServiceTest, FileServiceTest
    └── validator/     # LoanValidatorTest
```

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
