package com.driveease.model;

public abstract class Vehicle {
    private int id;
    private String registrationNumber;
    private String brand;
    private String model;
    private double dailyRate;
    private VehicleStatus status;

    protected Vehicle(int id, String registrationNumber, String brand,
                      String model, double dailyRate, VehicleStatus status) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.dailyRate = dailyRate;
        this.status = status;
    }

    public abstract String getCategory();

    public abstract double calculateRentalCost(long days);

    public int getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public String getDisplayName() {
        return brand + " " + model + " (" + registrationNumber + ")";
    }
}
