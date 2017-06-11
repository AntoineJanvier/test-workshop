package com.example.integration;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

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
            .name("bob")
            .role(Role.USER)
            .password("toto")
            .build();

    private final User user3= User.builder()
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
        assertThat(userRepository.getUser(user).getId()).isGreaterThan(0);
    }

    @Test
    public void should_get_an_existing_user() throws SQLException {

        userRepository.getUser(user);

        assertThat(userRepository.getUser(user)).isEqualTo(null);
    }
    
    @Test
    public void should_users_is_equal() {


        userRepository.insertUser(user2);
        userRepository.insertUser(user3);

        int counter = countOnTable("user");

        assertThat(counter).isEqualTo(3);

        assertThat(userRepository.getUser(user).getId()).isGreaterThan(0);
        assertThat(userRepository.getUser(user2).getId()).isGreaterThan(0);
        assertThat(userRepository.getUser(user3).getId()).isGreaterThan(0);

        assertThat(userRepository.getUser(user).isEqual(userRepository.getUser(user3))).isEqualTo(true);
        assertThat(userRepository.getUser(user).isEqual(userRepository.getUser(user2))).isEqualTo(false);

    }

}