package com.example.integration;

import org.junit.*;

import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class DatabaseTest {

    static DatabaseManager databaseManager;

    @BeforeClass
    public static void initConnection() throws SQLException {
        databaseManager = new DatabaseManager();
        databaseManager.getConnection();
    }

    @Before
    public void init() {
        databaseManager.clear("user");
    }

    @After
    public void teardown() {
        databaseManager.clear("user");
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        databaseManager.close();
    }

    public int countOnTable(String table) throws SQLException {
        return databaseManager.countOn(table);
    }

    @Test
    public void should_connect_to_database() throws SQLException {
        final Connection connection = databaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO user (name, type, pwd) VALUES ('John', 'ADMIN', 'toto')");

        final int insertedRows = statement.executeUpdate();

        assertThat(insertedRows).isEqualTo(1);
    }
}