package com.driveease.model;

import java.time.LocalDate;

public class Rental {
    private int id;
    private int customerId;
    private int vehicleId;
    private LocalDate startDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    private RentalStatus status;
    private double baseAmount;
    private double lateFee;
    private double totalAmount;

    public Rental(int id, int customerId, int vehicleId,
                  LocalDate startDate, LocalDate expectedReturnDate,
                  LocalDate actualReturnDate, RentalStatus status,
                  double baseAmount, double lateFee, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
        this.status = status;
        this.baseAmount = baseAmount;
        this.lateFee = lateFee;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public double getLateFee() {
        return lateFee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
