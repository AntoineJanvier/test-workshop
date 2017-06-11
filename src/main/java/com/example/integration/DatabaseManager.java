package com.example.integration;

import java.sql.*;

public class DatabaseManager {
    private Connection connection;

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/projectunittests";
                String user = "root";
                String password = "root";
                connection = DriverManager.getConnection(url, user, password);
//                System.out.println("Connection acquired");
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void clear(String tableName) {
        try {
            if (connection == null || connection.isClosed())
                getConnection();
            connection.createStatement().executeUpdate("truncate " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countOn(String table) {
        getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) as total from " + table);
            resultSet.next();

            return resultSet.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}