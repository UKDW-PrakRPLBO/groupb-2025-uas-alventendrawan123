package org.uas.repository;

import org.uas.data.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public boolean insertUser(String email, String username, String password) throws SQLException {
        String query = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, username);
        stmt.setString(3, password);
        return stmt.executeUpdate() > 0;
    }

    public boolean updateUser(String email, String username, String password) throws SQLException {
        String query = "UPDATE users SET username = ?, password = ? WHERE email = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, email);
        return stmt.executeUpdate() > 0;
    }

    public boolean deleteUser(String email) throws SQLException {
        String query = "DELETE FROM users WHERE email = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, email);
        return stmt.executeUpdate() > 0;
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            users.add(new User(rs.getString("email"), rs.getString("username"), rs.getString("password")));
        }
        return users;
    }

    public boolean authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }
}
