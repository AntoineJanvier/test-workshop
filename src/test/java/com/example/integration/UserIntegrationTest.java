package com.example.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;

import static org.assertj.core.api.Assertions.assertThat;

public class UserIntegrationTest extends DatabaseTest {

    private UserRepository userRepository;
    private final User user = User.builder()
            .name("Johny")
            .role(Role.USER)
            .password("toto")
            .build();

    private final User user2 = User.builder()
            .name("bob")
            .role(Role.USER)
            .password("toto")
            .build();

    private final User user3 = User.builder()
            .name("Johny")
            .role(Role.USER)
            .password("toto")
            .build();

    @Before
    public void initRepo() {
        userRepository = new UserRepository(databaseManager);
    }

    @Test
    public void should_insert_user() throws SQLException {

        int beforeCounter = countOnTable("user");

        userRepository.insertUser(user);

        int counter = countOnTable("user");

        assertThat(beforeCounter).isEqualTo(0);
        assertThat(counter).isEqualTo(1);
    }

    @Test
    public void should_get_an_existing_user() throws SQLException {

        userRepository.insertUser(user);
        ResultSet rs = userRepository.getUser(user.getName(), user.getPassword());

        assertThat(rs).isNotNull();
    }

    @Test
    public void should_users_is_equal() throws SQLException {

        userRepository.insertUser(user);
        userRepository.insertUser(user2);
        userRepository.insertUser(user3);

        int counter = countOnTable("user");

        assertThat(counter).isEqualTo(3);

        assertThat(userRepository.getUser(user.getName(), user.getPassword())).isNotNull();
        assertThat(userRepository.getUser(user2.getName(), user2.getPassword())).isNotNull();
        assertThat(userRepository.getUser(user3.getName(), user3.getPassword())).isNotNull();
    }

    @After
    public void clean_repo() {
        userRepository.database.clear("user");
    }
}