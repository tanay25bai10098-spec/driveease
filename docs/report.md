# DriveEase Vehicle Rental Management System

## 1. Introduction

Vehicle rental businesses require an organized system to manage vehicles, customers, bookings, returns, and payments. Manual processing can result in incorrect calculations, duplicate bookings, and difficulty tracking vehicle availability.

DriveEase is a Java-based vehicle rental management system that automates these activities using object-oriented programming principles and SQLite database storage.

## 2. Problem Statement

Manual rental management makes it difficult to track vehicle availability, customer information, rental periods, and payment details. The proposed system provides a centralized application for managing the complete rental workflow.

## 3. Objectives

- Automate vehicle rental operations
- Track vehicle availability
- Maintain customer records
- Calculate rental charges automatically
- Calculate late return penalties
- Generate invoices
- Apply Java OOP principles

## 4. Functional Requirements

### Vehicle Management

- Add vehicles
- View vehicles
- View available vehicles
- Delete available vehicles
- Support cars, bikes, and SUVs

### Customer Management

- Register customers
- View customers
- Validate required fields
- Prevent duplicate email addresses

### Rental Management

- Create rental bookings
- Check vehicle availability
- Store expected return dates
- Return vehicles
- Mark returned vehicles as available

### Billing

- Calculate base rental amount
- Calculate late fees
- Generate invoices
- Store payment-related rental information

## 5. Non-Functional Requirements

### Performance

Normal operations should complete within a few seconds.

### Usability

The system provides a simple menu-driven interface.

### Reliability

All rental and customer records are stored in SQLite.

### Maintainability

The project uses separate model, repository, service, and UI layers.

### Security

Database queries use prepared statements to prevent SQL injection.

### Error Handling

Invalid dates, numbers, missing records, duplicate records, and unavailable vehicles are handled using validation and custom exceptions.

## 6. System Architecture

The application follows a layered architecture:

1. Presentation Layer
2. Service Layer
3. Repository Layer
4. Database Layer

The presentation layer receives user input. Services apply business rules. Repositories communicate with SQLite. Model classes represent system data.

## 7. OOP Design

### Encapsulation

Model fields are private and accessed through public methods.

### Inheritance

`Car`, `Bike`, and `SUV` extend the abstract `Vehicle` class.

### Polymorphism

Each vehicle type implements its own rental-cost calculation.

### Abstraction

The `Vehicle` class defines common properties and abstract behavior.

### Composition

Rental records connect customers and vehicles.

## 8. Database Design

The database contains three main tables:

- Customers
- Vehicles
- Rentals

A customer can have multiple rentals. A vehicle can be associated with multiple rental records over time.

## 9. Implementation Details

The project is implemented using Java 17. JDBC is used to connect to SQLite. Maven manages dependencies. JUnit is used for unit testing.

Main components include:

- `Vehicle`
- `Car`
- `Bike`
- `SUV`
- `Customer`
- `Rental`
- `VehicleRepository`
- `CustomerRepository`
- `RentalRepository`
- `RentalService`
- `BillingService`

## 10. Testing Approach

The project uses unit testing to verify:

- Vehicle rental cost calculation
- Late fee calculation
- Input validation
- Database operations

Example test cases:

| Test Case | Expected Result |
|---|---|
| Calculate three-day car rental | Correct base amount |
| Calculate two late days | Correct late fee |
| Rent unavailable vehicle | Error message |
| Return completed rental | Validation error |
| Register duplicate email | Database error |

## 11. Challenges Faced

- Designing relationships between rental, vehicle, and customer data
- Applying inheritance to different vehicle types
- Handling rental date calculations
- Maintaining vehicle status during the rental lifecycle
- Connecting Java classes with SQLite

## 12. Learnings

- Practical use of inheritance and polymorphism
- Database connectivity using JDBC
- Layered application architecture
- Exception handling and input validation
- Unit testing with JUnit
- Git-based project organization

## 13. Future Enhancements

- JavaFX graphical user interface
- Login and role-based authentication
- Online payment integration
- Email and SMS notifications
- Vehicle maintenance module
- Revenue charts and analytics
- Export invoices as PDF

## 14. References

- Oracle Java Documentation
- SQLite Documentation
- JDBC Documentation
- JUnit 5 Documentation
- Maven Documentation
