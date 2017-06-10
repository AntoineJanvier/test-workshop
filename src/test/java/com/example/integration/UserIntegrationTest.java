package com.example.integration;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserIntegrationTest extends DatabaseTest {

    UserRepository userRepository;

    @Before
    public void initRepo() {
        userRepository = new UserRepository(databaseManager);
    }

    @Test
    public void should_insert_user() throws SQLException {
        final User user = User.builder()
                .name("Johny")
                .role(Role.USER)
                .password("toto")
                .build();
        int beforeCounter = countOnTable("user");

        userRepository.insertUser(user);

        int counter = countOnTable("user");

        assertThat(beforeCounter).isEqualTo(0);
        assertThat(counter).isEqualTo(1);
        assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    public void should_get_an_existing_user() {

    }
}