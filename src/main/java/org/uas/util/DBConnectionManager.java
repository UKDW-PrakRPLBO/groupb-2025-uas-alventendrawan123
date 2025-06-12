package org.uas.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static Connection connection;

    private DBConnectionManager() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:dbuas.db");
                connection.createStatement().executeUpdate(
                        "CREATE TABLE IF NOT EXISTS users (" +
                                "email TEXT PRIMARY KEY, " +
                                "username TEXT, " +
                                "password TEXT)"
                );
                // Insert admin default jika belum ada
                connection.createStatement().executeUpdate(
                        "INSERT OR IGNORE INTO users (email, username, password) VALUES ('admin@admin.com', 'admin', 'admin123')"
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
