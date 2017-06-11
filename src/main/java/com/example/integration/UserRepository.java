package com.example.integration;

import java.sql.*;

public class UserRepository {

    DatabaseManager database;

    public UserRepository(DatabaseManager database) {
        this.database = database;
    }

    public User insertUser(User user) throws SQLException {
        final Connection connection = database.getConnection();
        final String sql = "INSERT INTO user (name, type, pwd) VALUES (?, ?, ?);";

        final PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getName());
        statement.setString(2, user.getRole().name());
        statement.setString(3, user.getPassword());
        statement.executeUpdate();
        final ResultSet resultSet = statement.getGeneratedKeys();

        if(resultSet.next()) {
            user.setId(resultSet.getLong(1));
            return user;
        }
        return null;
    }
    public User getUser(User user) throws SQLException {
        final Connection connection = database.getConnection();
        final String sql = "SELECT * FROM user WHERE name=" + user.getName() + " AND pwd=" + user.getPassword() + ";";

        final PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.execute();
        final ResultSet resultSet = statement.getGeneratedKeys();

        System.out.println(resultSet.toString());

        return null;
    }
}