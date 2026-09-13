package com.driveease.repository;

import com.driveease.db.DatabaseConnection;
import com.driveease.exception.ApplicationException;
import com.driveease.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleRepository {

    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles ORDER BY id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                vehicles.add(map(rs));
            }
            return vehicles;
        } catch (SQLException e) {
            throw new ApplicationException("Unable to load vehicles.", e);
        }
    }

    public List<Vehicle> findAvailable() {
        return findAll().stream()
                .filter(v -> v.getStatus() == VehicleStatus.AVAILABLE)
                .toList();
    }

    public Optional<Vehicle> findById(int id) {
        String sql = "SELECT * FROM vehicles WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new ApplicationException("Unable to find vehicle.", e);
        }
    }

    public void save(Vehicle vehicle) {
        String sql = """
                INSERT INTO vehicles
                (category, registration_no, brand, model, daily_rate,
                 seats, engine_cc, four_wheel_drive, status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            int seats = 0;
            int engineCc = 0;
            int fourWheelDrive = 0;

            if (vehicle instanceof Car car) {
                seats = car.getSeats();
            } else if (vehicle instanceof Bike bike) {
                engineCc = bike.getEngineCc();
            } else if (vehicle instanceof SUV suv) {
                seats = suv.getSeats();
                fourWheelDrive = suv.isFourWheelDrive() ? 1 : 0;
            }

            ps.setString(1, vehicle.getCategory());
            ps.setString(2, vehicle.getRegistrationNumber());
            ps.setString(3, vehicle.getBrand());
            ps.setString(4, vehicle.getModel());
            ps.setDouble(5, vehicle.getDailyRate());
            ps.setInt(6, seats);
            ps.setInt(7, engineCc);
            ps.setInt(8, fourWheelDrive);
            ps.setString(9, vehicle.getStatus().name());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ApplicationException(
                    "Unable to save vehicle. Registration may already exist.", e);
        }
    }

    public void updateStatus(int id, VehicleStatus status) {
        String sql = "UPDATE vehicles SET status = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ApplicationException("Unable to update vehicle status.", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM vehicles WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new ApplicationException("Unable to delete vehicle.", e);
        }
    }

    private Vehicle map(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String category = rs.getString("category");
        String registration = rs.getString("registration_no");
        String brand = rs.getString("brand");
        String model = rs.getString("model");
        double rate = rs.getDouble("daily_rate");
        VehicleStatus status =
                VehicleStatus.valueOf(rs.getString("status"));

        return switch (category) {
            case "BIKE" -> new Bike(id, registration, brand, model,
                    rate, status, rs.getInt("engine_cc"));
            case "SUV" -> new SUV(id, registration, brand, model,
                    rate, status, rs.getInt("seats"),
                    rs.getInt("four_wheel_drive") == 1);
            default -> new Car(id, registration, brand, model,
                    rate, status, rs.getInt("seats"));
        };
    }
}
