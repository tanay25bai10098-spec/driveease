# DriveEase Vehicle Rental Management System

## Overview

DriveEase is a Java-based vehicle rental management system developed using object-oriented programming principles.

The system allows rental staff to manage vehicles, customers, rentals, vehicle returns, invoices, and rental reports.

## Features

- Add and delete vehicles
- Support for cars, bikes, and SUVs
- Register customers
- View available vehicles
- Create rental bookings
- Return rented vehicles
- Automatic rental cost calculation
- Late fee calculation
- Invoice generation
- SQLite database storage
- Input validation and exception handling
- Unit testing using JUnit

## Technologies Used

- Java 17
- Maven
- SQLite
- JDBC
- JUnit 5
- Git and GitHub

## OOP Concepts Used

- Encapsulation
- Inheritance
- Polymorphism
- Abstraction
- Interfaces and service-based design
- Composition
- Exception handling

## Requirements

- Java JDK 17 or higher
- Maven 3.8 or higher
- Git

## How to Run

Clone the repository:

```bash
git clone https://github.com/YOUR_USERNAME/driveease.git
cd driveease
```

Compile the project:

```bash
mvn clean compile
```

Run the application:

```bash
mvn exec:java
```

The SQLite database file `driveease.db` will be generated automatically.

## Run Tests

```bash
mvn test
```

## Database

The application uses SQLite. Database tables are automatically created on the first run.

## Project Modules

1. Vehicle Management
2. Customer Management
3. Rental Management
4. Billing and Invoice Management
5. Reporting and Rental Tracking

## Future Enhancements

- JavaFX graphical interface
- Admin authentication
- Online payment integration
- Email notifications
- Advanced revenue reports
- Vehicle maintenance scheduling
