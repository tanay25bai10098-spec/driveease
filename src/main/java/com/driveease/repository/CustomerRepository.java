package com.driveease.repository;

import com.driveease.db.DatabaseConnection;
import com.driveease.exception.ApplicationException;
import com.driveease.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository {

    public void save(Customer customer) {
        String sql = "INSERT INTO customers(name, phone, email) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getEmail());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new ApplicationException(
                    "Unable to save customer. Email may already exist.", e);
        }
    }

    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }

            return customers;
        } catch (SQLException e) {
            throw new ApplicationException("Unable to load customers.", e);
        }
    }

    public Optional<Customer> findById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Customer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email")
                    ));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new ApplicationException("Unable to find customer.", e);
        }
    }
}
