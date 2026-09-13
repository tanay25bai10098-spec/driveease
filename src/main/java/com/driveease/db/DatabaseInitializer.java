package com.driveease.db;

import com.driveease.exception.ApplicationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void initialize() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            createTables(connection);
            insertSampleVehicles(connection);
        } catch (SQLException e) {
            throw new ApplicationException("Database initialization failed.", e);
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        String vehicles = """
                CREATE TABLE IF NOT EXISTS vehicles (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    category TEXT NOT NULL,
                    registration_no TEXT NOT NULL UNIQUE,
                    brand TEXT NOT NULL,
                    model TEXT NOT NULL,
                    daily_rate REAL NOT NULL,
                    seats INTEGER DEFAULT 0,
                    engine_cc INTEGER DEFAULT 0,
                    four_wheel_drive INTEGER DEFAULT 0,
                    status TEXT NOT NULL
                )
                """;

        String customers = """
                CREATE TABLE IF NOT EXISTS customers (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    phone TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE
                )
                """;

        String rentals = """
                CREATE TABLE IF NOT EXISTS rentals (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    customer_id INTEGER NOT NULL,
                    vehicle_id INTEGER NOT NULL,
                    start_date TEXT NOT NULL,
                    expected_return_date TEXT NOT NULL,
                    actual_return_date TEXT,
                    status TEXT NOT NULL,
                    base_amount REAL NOT NULL,
                    late_fee REAL NOT NULL DEFAULT 0,
                    total_amount REAL NOT NULL,
                    FOREIGN KEY(customer_id) REFERENCES customers(id),
                    FOREIGN KEY(vehicle_id) REFERENCES vehicles(id)
                )
                """;

        connection.createStatement().execute(vehicles);
        connection.createStatement().execute(customers);
        connection.createStatement().execute(rentals);
    }

    private static void insertSampleVehicles(Connection connection)
            throws SQLException {
        String countSql = "SELECT COUNT(*) FROM vehicles";

        try (var statement = connection.createStatement();
             var result = statement.executeQuery(countSql)) {

            if (result.next() && result.getInt(1) == 0) {
                String sql = """
                        INSERT INTO vehicles
                        (category, registration_no, brand, model, daily_rate,
                         seats, engine_cc, four_wheel_drive, status)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                        """;

                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    insertVehicle(ps, "CAR", "CAR-101", "Toyota",
                            "Corolla", 1800, 5, 0, 0);
                    insertVehicle(ps, "BIKE", "BIKE-202", "Honda",
                            "Shine", 700, 0, 125, 0);
                    insertVehicle(ps, "SUV", "SUV-303", "Mahindra",
                            "XUV700", 3200, 7, 0, 1);
                }
            }
        }
    }

    private static void insertVehicle(PreparedStatement ps, String category,
                                      String registration, String brand,
                                      String model, double rate, int seats,
                                      int engineCc, int fourWheelDrive)
            throws SQLException {
        ps.setString(1, category);
        ps.setString(2, registration);
        ps.setString(3, brand);
        ps.setString(4, model);
        ps.setDouble(5, rate);
        ps.setInt(6, seats);
        ps.setInt(7, engineCc);
        ps.setInt(8, fourWheelDrive);
        ps.setString(9, "AVAILABLE");
        ps.executeUpdate();
    }
}
