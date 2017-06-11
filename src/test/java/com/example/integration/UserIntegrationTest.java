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
        final User user = User.builder()
                .name("boby")
                .role(Role.USER)
                .password("toto")
                .build();

        int beforeCounter = countOnTable("user");

        userRepository.insertUser(user);

        int counter = countOnTable("user");


        assertThat(beforeCounter).isEqualTo(0);
        assertThat(counter).isEqualTo(1);
        assertThat(userRepository.getUser(user)).isEqualTo(null);
    }


    @Test
    public void should_users_is_equal() {
        final User user1 = User.builder()
                .name("boby")
                .role(Role.USER)
                .password("toto")
                .build();

        final User user2 = User.builder()
                .name("jony")
                .role(Role.USER)
                .password("toto")
                .build();

        final User user3 = User.builder()
                .name("boby")
                .role(Role.USER)
                .password("toto")
                .build();

        int beforeCounter = countOnTable("user");

        userRepository.insertUser(user1);
        userRepository.insertUser(user2);
        userRepository.insertUser(user3);

        int counter = countOnTable("user");


        assertThat(beforeCounter).isEqualTo(0);
        assertThat(counter).isEqualTo(3);
        assertThat(userRepository.getUser(user1).isEqual(userRepository.getUser(user3))).isEqualTo(true);
        assertThat(userRepository.getUser(user1).isEqual(userRepository.getUser(user2))).isEqualTo(false);
    }
}