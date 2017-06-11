package com.example;

import com.example.integration.DatabaseManager;
import com.example.integration.Role;
import com.example.integration.User;
import com.example.integration.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {

    private static Scanner sc = new Scanner(System.in);
    private static DatabaseManager databaseManager = new DatabaseManager();
    private static UserRepository userRepository = new UserRepository(databaseManager);

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(DemoApplication.class, args);

        int choice = -1;

        while (choice != 9) {
            print_main_menu();
            choice = sc.nextInt();

            if (choice != 9) {
                switch (choice) {
                    case 1:
                        subscribe_new_user();
                        break;
                    case 2:
                        authenticate_existing_user("FULL");
                        break;
                    case 3:
                        reset_password_of_user();
                        break;
                    default:
                        System.out.println("Bad entry, try again");
                }
            }
        }
    }

    private static void print_main_menu() {
        System.out.println("MAIN MENU\n\nChoose an action :\n");
        System.out.println("1 - Subscribe user\n"); // Just insert into the DB, need to implement hashed password
        System.out.println("2 - Authenticate\n"); // Check in DB (hashed password to implement) ID NAME ROLE
        System.out.println("3 - Reset password\n"); // Need to verify infos on user before reset
        System.out.println("9 - Quit\n");
        System.out.print("Choice : ");
    }

    private static boolean subscribe_new_user() throws SQLException {

        System.out.print("Type a username : ");
        String username = sc.next();
        System.out.print("Type a password : ");
        String password = sc.next();

        return false;
    }

    private static boolean authenticate_existing_user(String method) throws SQLException {
        if ("FULL".equals(method)) {
            System.out.print("Type username : ");
            String username = sc.next();
            System.out.print("Type password : ");
            String password = sc.next();

            User u = check_if_user_exists_in_db(username, password);

            if (u == null) {
                System.out.println("User not found !\n\n");
            }

        } else if ("RESET PASSWORD".equals(method)) {
            System.out.print("Type username : ");
            String username = sc.next();
        }
        return false;
    }

    private static void reset_password_of_user() throws SQLException {
        if (authenticate_existing_user("RESET PASSWORD")) {

        }
    }

    private static User check_if_user_exists_in_db(String username, String password) throws SQLException {
        ResultSet resultSet = userRepository.getUser(username, password);
        if (resultSet.getString(2).length() > 0)
            return User.builder()
                    .id((long) 900)
                    .name("Jacky")
                    .role(Role.USER)
                    .password("Michel")
                    .build();
        return null;
    }
}
