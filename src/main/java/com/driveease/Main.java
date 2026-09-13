package com.driveease;

import com.driveease.db.DatabaseInitializer;
import com.driveease.repository.CustomerRepository;
import com.driveease.repository.RentalRepository;
import com.driveease.repository.VehicleRepository;
import com.driveease.service.*;
import com.driveease.ui.ConsoleApp;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();

        VehicleRepository vehicleRepository = new VehicleRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        RentalRepository rentalRepository = new RentalRepository();

        VehicleService vehicleService =
                new VehicleService(vehicleRepository);

        CustomerService customerService =
                new CustomerService(customerRepository);

        BillingService billingService = new BillingService();

        RentalService rentalService = new RentalService(
                rentalRepository,
                vehicleRepository,
                vehicleService,
                customerService,
                billingService
        );

        ConsoleApp app = new ConsoleApp(
                vehicleService,
                customerService,
                rentalService,
                billingService
        );

        app.start();
    }
}
