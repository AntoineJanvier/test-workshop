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

        if (resultSet.next()) {
            user.setId(resultSet.getLong(1));
            return user;
        }
        return null;
    }

    public User getUser(String username, String password) throws SQLException {
        final Connection connection = database.getConnection();
        final String sql = "SELECT * FROM user WHERE name='" + username + "' AND pwd='" + password + "';";

        final PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.execute();
        final ResultSet resultSet = statement.getGeneratedKeys();

//        System.out.println(resultSet.toString());
//        ResultSetMetaData rsmd = resultSet.getMetaData();
//        int columnsNumber = rsmd.getColumnCount();
//        while (resultSet.next()) {
//            for (int i = 1; i <= columnsNumber; i++) {
//                if (i > 1) System.out.print(",  ");
//                String columnValue = resultSet.getString(i);
//                System.out.print(columnValue + " " + rsmd.getColumnName(i));
//            }
//            System.out.println("");
//        }

        if(resultSet.next()) {
            final User user = User.builder()
                    .name(resultSet.getString(2))
                    .role(Role.valueOf(resultSet.getString(3)))
                    .password(resultSet.getString(4))
                    .build();
            return user;
        }
        return null;
    }
}