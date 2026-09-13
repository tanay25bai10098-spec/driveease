package com.driveease.model;

public class Car extends Vehicle {
    private int seats;

    public Car(int id, String registrationNumber, String brand,
               String model, double dailyRate, VehicleStatus status,
               int seats) {
        super(id, registrationNumber, brand, model, dailyRate, status);
        this.seats = seats;
    }

    @Override
    public String getCategory() {
        return "CAR";
    }

    @Override
    public double calculateRentalCost(long days) {
        return getDailyRate() * Math.max(1, days);
    }

    public int getSeats() {
        return seats;
    }
}
