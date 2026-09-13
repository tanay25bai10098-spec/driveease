package com.driveease.model;

public class Bike extends Vehicle {
    private int engineCc;

    public Bike(int id, String registrationNumber, String brand,
                String model, double dailyRate, VehicleStatus status,
                int engineCc) {
        super(id, registrationNumber, brand, model, dailyRate, status);
        this.engineCc = engineCc;
    }

    @Override
    public String getCategory() {
        return "BIKE";
    }

    @Override
    public double calculateRentalCost(long days) {
        return getDailyRate() * Math.max(1, days) * 0.85;
    }

    public int getEngineCc() {
        return engineCc;
    }
}
