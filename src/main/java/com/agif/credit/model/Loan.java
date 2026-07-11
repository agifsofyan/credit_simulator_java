package com.agif.credit.model;

public class Loan {
    private final Vehicle vehicle;
    private final double totalLoanAmount;
    private final double downPayment;
    private final int loanTenure;

    public Loan(Vehicle vehicle, double totalLoanAmount, double downPayment, int loanTenure) {
        this.vehicle = vehicle;
        this.totalLoanAmount = totalLoanAmount;
        this.downPayment = downPayment;
        this.loanTenure = loanTenure;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public int getLoanTenure() {
        return loanTenure;
    }

    public double getPrincipalAmount() {
        return totalLoanAmount - downPayment;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "vehicle=" + vehicle +
                ", totalLoanAmount=" + totalLoanAmount +
                ", downPayment=" + downPayment +
                ", loanTenure=" + loanTenure +
                ", principalAmount=" + getPrincipalAmount() +
                '}';
    }
}
