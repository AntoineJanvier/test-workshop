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

    public ResultSet getUser(String username, String password) throws SQLException {
        final Connection connection = database.getConnection();
        final String sql = "SELECT * FROM user WHERE name='" + username + "' AND pwd='" + password + "';";

        final PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.execute();
        final ResultSet resultSet = statement.getGeneratedKeys();

        return resultSet;
//        if(resultSet.next()) {
//            long id = resultSet.getLong(1);
//            String name = resultSet.getString(2);
//            Role role = Role.valueOf(resultSet.getString(3));
//            String password2 = resultSet.getString(4);
//
//            final User user = User.builder()
//                    .id(id)
//                    .name(name)
//                    .role(role)
//                    .password(password2)
//                    .build();
//            return user;
//            return resultSet;
//        }
//        return User.builder()
//                .id((long) 9000)
//                .name("Fake")
//                .role(Role.USER)
//                .password("Fake")
//                .build();
//        return null;
    }
}