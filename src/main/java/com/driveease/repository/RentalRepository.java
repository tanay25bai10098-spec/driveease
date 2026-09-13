package com.driveease.repository;

import com.driveease.db.DatabaseConnection;
import com.driveease.exception.ApplicationException;
import com.driveease.model.Rental;
import com.driveease.model.RentalStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentalRepository {

    public int save(Rental rental) {
        String sql = """
                INSERT INTO rentals
                (customer_id, vehicle_id, start_date, expected_return_date,
                 status, base_amount, late_fee, total_amount)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, rental.getCustomerId());
            ps.setInt(2, rental.getVehicleId());
            ps.setString(3, rental.getStartDate().toString());
            ps.setString(4, rental.getExpectedReturnDate().toString());
            ps.setString(5, rental.getStatus().name());
            ps.setDouble(6, rental.getBaseAmount());
            ps.setDouble(7, rental.getLateFee());
            ps.setDouble(8, rental.getTotalAmount());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

            throw new ApplicationException("Rental ID could not be generated.");

        } catch (SQLException e) {
            throw new ApplicationException("Unable to create rental.", e);
        }
    }

    public Optional<Rental> findById(int id) {
        String sql = "SELECT * FROM rentals WHERE id = ?";

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
            throw new ApplicationException("Unable to find rental.", e);
        }
    }

    public List<Rental> findActive() {
        return findByStatus(RentalStatus.ACTIVE);
    }

    public List<Rental> findAll() {
        String sql = "SELECT * FROM rentals ORDER BY id DESC";
        return findUsingQuery(sql);
    }

    public void completeRental(int id, LocalDate actualReturnDate,
                               double lateFee, double totalAmount) {
        String sql = """
                UPDATE rentals
                SET actual_return_date = ?, status = ?, late_fee = ?,
                    total_amount = ?
                WHERE id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, actualReturnDate.toString());
            ps.setString(2, RentalStatus.COMPLETED.name());
            ps.setDouble(3, lateFee);
            ps.setDouble(4, totalAmount);
            ps.setInt(5, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ApplicationException("Unable to complete rental.", e);
        }
    }

    private List<Rental> findByStatus(RentalStatus status) {
        String sql = "SELECT * FROM rentals WHERE status = ? ORDER BY id DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status.name());

            try (ResultSet rs = ps.executeQuery()) {
                List<Rental> rentals = new ArrayList<>();

                while (rs.next()) {
                    rentals.add(map(rs));
                }

                return rentals;
            }
        } catch (SQLException e) {
            throw new ApplicationException("Unable to load rentals.", e);
        }
    }

    private List<Rental> findUsingQuery(String sql) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Rental> rentals = new ArrayList<>();

            while (rs.next()) {
                rentals.add(map(rs));
            }

            return rentals;
        } catch (SQLException e) {
            throw new ApplicationException("Unable to load rentals.", e);
        }
    }

    private Rental map(ResultSet rs) throws SQLException {
        String actualDate = rs.getString("actual_return_date");

        return new Rental(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getInt("vehicle_id"),
                LocalDate.parse(rs.getString("start_date")),
                LocalDate.parse(rs.getString("expected_return_date")),
                actualDate == null ? null : LocalDate.parse(actualDate),
                RentalStatus.valueOf(rs.getString("status")),
                rs.getDouble("base_amount"),
                rs.getDouble("late_fee"),
                rs.getDouble("total_amount")
        );
    }
}
