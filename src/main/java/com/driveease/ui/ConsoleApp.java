package com.driveease.ui;

import com.driveease.exception.ApplicationException;
import com.driveease.model.*;
import com.driveease.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private final VehicleService vehicleService;
    private final CustomerService customerService;
    private final RentalService rentalService;
    private final BillingService billingService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleApp(VehicleService vehicleService,
                     CustomerService customerService,
                     RentalService rentalService,
                     BillingService billingService) {
        this.vehicleService = vehicleService;
        this.customerService = customerService;
        this.rentalService = rentalService;
        this.billingService = billingService;
    }

    public void start() {
        boolean running = true;

        System.out.println("\n===== Welcome to DriveEase =====");

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1 -> listVehicles(false);
                    case 2 -> listVehicles(true);
                    case 3 -> addVehicle();
                    case 4 -> deleteVehicle();
                    case 5 -> addCustomer();
                    case 6 -> listCustomers();
                    case 7 -> createRental();
                    case 8 -> returnVehicle();
                    case 9 -> listActiveRentals();
                    case 10 -> printInvoice();
                    case 0 -> running = false;
                    default -> System.out.println("Invalid option.");
                }
            } catch (ApplicationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Thank you for using DriveEase.");
    }

    private void printMenu() {
        System.out.println("""
                
                --------------- MAIN MENU ---------------
                1. List all vehicles
                2. List available vehicles
                3. Add vehicle
                4. Delete vehicle
                5. Register customer
                6. List customers
                7. Create rental
                8. Return vehicle
                9. View active rentals
                10. Print invoice
                0. Exit
                ------------------------------------------
                """);
    }

    private void listVehicles(boolean availableOnly) {
        List<Vehicle> vehicles = availableOnly
                ? vehicleService.getAvailableVehicles()
                : vehicleService.getAllVehicles();

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }

        System.out.printf("%-4s %-8s %-18s %-12s %-12s %-10s%n",
                "ID", "TYPE", "VEHICLE", "REGISTRATION",
                "RATE/DAY", "STATUS");

        for (Vehicle vehicle : vehicles) {
            System.out.printf("%-4d %-8s %-18s %-12s %-10.2f %-10s%n",
                    vehicle.getId(),
                    vehicle.getCategory(),
                    vehicle.getBrand() + " " + vehicle.getModel(),
                    vehicle.getRegistrationNumber(),
                    vehicle.getDailyRate(),
                    vehicle.getStatus());
        }
    }

    private void addVehicle() {
        String category = readRequired(
                "Category (CAR/BIKE/SUV): ").toUpperCase();

        String registration = readRequired("Registration number: ");
        String brand = readRequired("Brand: ");
        String model = readRequired("Model: ");
        double rate = readDouble("Base daily rate: ");

        Vehicle vehicle;

        switch (category) {
            case "CAR" -> {
                int seats = readInt("Number of seats: ");
                vehicle = new Car(0, registration, brand, model, rate,
                        VehicleStatus.AVAILABLE, seats);
            }
            case "BIKE" -> {
                int engine = readInt("Engine capacity in CC: ");
                vehicle = new Bike(0, registration, brand, model, rate,
                        VehicleStatus.AVAILABLE, engine);
            }
            case "SUV" -> {
                int seats = readInt("Number of seats: ");
                boolean fourWheel = readRequired(
                        "Four-wheel drive? (y/n): ")
                        .equalsIgnoreCase("y");

                vehicle = new SUV(0, registration, brand, model, rate,
                        VehicleStatus.AVAILABLE, seats, fourWheel);
            }
            default -> throw new ApplicationException(
                    "Invalid vehicle category.");
        }

        vehicleService.addVehicle(vehicle);
        System.out.println("Vehicle added successfully.");
    }

    private void deleteVehicle() {
        listVehicles(false);
        int id = readInt("Vehicle ID to delete: ");
        vehicleService.deleteVehicle(id);
        System.out.println("Vehicle deleted successfully.");
    }

    private void addCustomer() {
        String name = readRequired("Customer name: ");
        String phone = readRequired("Phone number: ");
        String email = readRequired("Email address: ");

        customerService.registerCustomer(
                new Customer(0, name, phone, email));

        System.out.println("Customer registered successfully.");
    }

    private void listCustomers() {
        List<Customer> customers = customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Customer customer : customers) {
            System.out.printf(
                    "ID: %d | Name: %s | Phone: %s | Email: %s%n",
                    customer.getId(),
                    customer.getName(),
                    customer.getPhone(),
                    customer.getEmail());
        }
    }

    private void createRental() {
        System.out.println("\nCustomers:");
        listCustomers();

        int customerId = readInt("Customer ID: ");

        System.out.println("\nAvailable vehicles:");
        listVehicles(true);

        int vehicleId = readInt("Vehicle ID: ");
        LocalDate startDate = readDate(
                "Start date (yyyy-MM-dd): ");
        LocalDate returnDate = readDate(
                "Expected return date (yyyy-MM-dd): ");

        int rentalId = rentalService.createRental(
                customerId, vehicleId, startDate, returnDate);

        System.out.println("Rental created successfully. Rental ID: "
                + rentalId);
    }

    private void returnVehicle() {
        listActiveRentals();

        int rentalId = readInt("Rental ID: ");
        LocalDate returnDate = readDate(
                "Actual return date (yyyy-MM-dd): ");

        Rental rental = rentalService.returnVehicle(rentalId, returnDate);
        Customer customer = customerService.getCustomer(
                rental.getCustomerId());
        Vehicle vehicle = vehicleService.getVehicle(
                rental.getVehicleId());

        System.out.println(
                billingService.generateInvoice(rental, customer, vehicle));
    }

    private void listActiveRentals() {
        List<Rental> rentals = rentalService.getActiveRentals();

        if (rentals.isEmpty()) {
            System.out.println("No active rentals.");
            return;
        }

        for (Rental rental : rentals) {
            System.out.printf(
                    "Rental ID: %d | Customer ID: %d | Vehicle ID: %d | "
                            + "Expected Return: %s | Amount: %.2f%n",
                    rental.getId(),
                    rental.getCustomerId(),
                    rental.getVehicleId(),
                    rental.getExpectedReturnDate(),
                    rental.getTotalAmount());
        }
    }

    private void printInvoice() {
        int rentalId = readInt("Rental ID: ");
        Rental rental = rentalService.getRental(rentalId);
        Customer customer = customerService.getCustomer(
                rental.getCustomerId());
        Vehicle vehicle = vehicleService.getVehicle(
                rental.getVehicleId());

        System.out.println(
                billingService.generateInvoice(rental, customer, vehicle));
    }

    private String readRequired(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();

            if (!value.isBlank()) {
                return value;
            }

            System.out.println("This field is required.");
        }
    }

    private int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    private LocalDate readDate(String message) {
        while (true) {
            try {
                System.out.print(message);
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println(
                        "Use the format yyyy-MM-dd.");
            }
        }
    }
}
