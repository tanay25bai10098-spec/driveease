package com.driveease.service;

import com.driveease.exception.ApplicationException;
import com.driveease.model.Vehicle;
import com.driveease.model.VehicleStatus;
import com.driveease.repository.VehicleRepository;

import java.util.List;

public class VehicleService {
    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehicle.getRegistrationNumber().isBlank()) {
            throw new ApplicationException("Registration number is required.");
        }

        if (vehicle.getDailyRate() <= 0) {
            throw new ApplicationException("Daily rate must be positive.");
        }

        repository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return repository.findAll();
    }

    public List<Vehicle> getAvailableVehicles() {
        return repository.findAvailable();
    }

    public Vehicle getVehicle(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        "Vehicle not found."));
    }

    public void deleteVehicle(int id) {
        Vehicle vehicle = getVehicle(id);

        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new ApplicationException(
                    "Only available vehicles can be deleted.");
        }

        if (!repository.delete(id)) {
            throw new ApplicationException("Vehicle could not be deleted.");
        }
    }
}
