DriveEase Vehicle Rental Management System README
Overview
DriveEase is a Java-based vehicle rental management system built on core OOP concepts. This application enables rental staff members to monitor and control company's rental processes effectively while also managing a vehicle fleet efficiently.
The program includes the following functionalities: l
Adding and deleting various types of vehicles including cars, bikes, and SUVs l
Registering customers l
Viewing available vehicles l
Booking rental seats l
Returning rented vehicles l
Calculating rental costs and late fees l
Generating invoices l
Storing data in an SQLite database l
Validating input data l
Testing the code using unit testing framework
Technologies
l Java 17 l Maven l SQLite l JDBC l JUnit 5 l Git and GitHub
OOP Concepts
l Encapsulation l Inheritance l Polymorphism
l Abstraction l Interface and service-based design l Composition l Exception handling
Requirements
l Java JDK 17 or higher l Maven 3.8 or higher l Git
How to Run
Clone the repository:
git clone https://github.com/YOUR_USERNAME/driveease.git cd driveease
Compile the project:
mvn clean compile
Run the application:
mvn exec:java The SQLite database file driveease.db will be generated automatically.
Run Tests
mvn test
Database
The application uses SQLite as a database engine. The necessary tables will be created automatically on the first launch of the application.
Project Modules
l Vehicle Management l Customer Management l Rental Management l Billing and Invoice Management l Reporting and Rental Tracking
Future Enhancements
l JavaFX graphical interface l Admin authentication l Online payment integration l Email notifications l Advanced revenue reports l Vehicle maintenance scheduling
Source: github.com/tanay25bai10098-spec/driveease
