package com.driveease.service;

import com.driveease.model.Customer;
import com.driveease.model.Rental;
import com.driveease.model.Vehicle;

import java.util.Locale;

public class BillingService {

    public double calculateLateFee(Vehicle vehicle, long lateDays) {
        return round(vehicle.getDailyRate() * lateDays * 0.20);
    }

    public String generateInvoice(Rental rental, Customer customer,
                                  Vehicle vehicle) {
        return String.format(Locale.US, """
                
                ================= DRIVE-EASE INVOICE =================
                Rental ID       : %d
                Customer        : %s
                Vehicle         : %s
                Rental Period   : %s to %s
                Return Date     : %s
                Base Amount     : %.2f
                Late Fee        : %.2f
                Total Amount    : %.2f
                Status          : %s
                =======================================================
                """,
                rental.getId(),
                customer.getName(),
                vehicle.getDisplayName(),
                rental.getStartDate(),
                rental.getExpectedReturnDate(),
                rental.getActualReturnDate() == null
                        ? "Not returned"
                        : rental.getActualReturnDate(),
                rental.getBaseAmount(),
                rental.getLateFee(),
                rental.getTotalAmount(),
                rental.getStatus());
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
