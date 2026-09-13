package com.driveease.model;

public class SUV extends Vehicle {
    private int seats;
    private boolean fourWheelDrive;

    public SUV(int id, String registrationNumber, String brand,
               String model, double dailyRate, VehicleStatus status,
               int seats, boolean fourWheelDrive) {
        super(id, registrationNumber, brand, model, dailyRate, status);
        this.seats = seats;
        this.fourWheelDrive = fourWheelDrive;
    }

    @Override
    public String getCategory() {
        return "SUV";
    }

    @Override
    public double calculateRentalCost(long days) {
        return getDailyRate() * Math.max(1, days) * 1.25;
    }

    public int getSeats() {
        return seats;
    }

    public boolean isFourWheelDrive() {
        return fourWheelDrive;
    }
}
