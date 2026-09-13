package com.driveease.db;

import com.driveease.exception.ApplicationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:driveease.db";

    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL);
            connection.createStatement().execute("PRAGMA foreign_keys = ON");
            return connection;
        } catch (SQLException e) {
            throw new ApplicationException("Unable to connect to database.", e);
        }
    }
}
