package com.driveease.service;

import com.driveease.exception.ApplicationException;
import com.driveease.model.*;
import com.driveease.repository.RentalRepository;
import com.driveease.repository.VehicleRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RentalService {
    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;
    private final CustomerService customerService;
    private final BillingService billingService;

    public RentalService(RentalRepository rentalRepository,
                         VehicleRepository vehicleRepository,
                         VehicleService vehicleService,
                         CustomerService customerService,
                         BillingService billingService) {
        this.rentalRepository = rentalRepository;
        this.vehicleRepository = vehicleRepository;
        this.vehicleService = vehicleService;
        this.customerService = customerService;
        this.billingService = billingService;
    }

    public int createRental(int customerId, int vehicleId,
                            LocalDate startDate,
                            LocalDate expectedReturnDate) {

        customerService.getCustomer(customerId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);

        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new ApplicationException("Vehicle is not available.");
        }

        if (expectedReturnDate.isBefore(startDate)) {
            throw new ApplicationException(
                    "Return date cannot be before start date.");
        }

        long days = Math.max(1,
                ChronoUnit.DAYS.between(startDate, expectedReturnDate));

        double baseAmount = vehicle.calculateRentalCost(days);

        Rental rental = new Rental(
                0,
                customerId,
                vehicleId,
                startDate,
                expectedReturnDate,
                null,
                RentalStatus.ACTIVE,
                baseAmount,
                0,
                baseAmount
        );

        int rentalId = rentalRepository.save(rental);
        vehicleRepository.updateStatus(vehicleId, VehicleStatus.RENTED);

        return rentalId;
    }

    public Rental returnVehicle(int rentalId, LocalDate actualReturnDate) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ApplicationException(
                        "Rental not found."));

        if (rental.getStatus() != RentalStatus.ACTIVE) {
            throw new ApplicationException("Rental is already completed.");
        }

        if (actualReturnDate.isBefore(rental.getStartDate())) {
            throw new ApplicationException(
                    "Return date cannot be before rental start date.");
        }

        Vehicle vehicle = vehicleService.getVehicle(rental.getVehicleId());

        long lateDays = Math.max(0,
                ChronoUnit.DAYS.between(
                        rental.getExpectedReturnDate(),
                        actualReturnDate));

        double lateFee = billingService.calculateLateFee(vehicle, lateDays);
        double totalAmount = rental.getBaseAmount() + lateFee;

        rentalRepository.completeRental(
                rentalId, actualReturnDate, lateFee, totalAmount);

        vehicleRepository.updateStatus(
                vehicle.getId(), VehicleStatus.AVAILABLE);

        return rentalRepository.findById(rentalId).orElseThrow();
    }

    public List<Rental> getActiveRentals() {
        return rentalRepository.findActive();
    }

    public Rental getRental(int id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        "Rental not found."));
    }
}
