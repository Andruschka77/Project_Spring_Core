package org.example.operations;

import org.example.operations.processors.CreateAccountProcessor;
import org.example.operations.processors.CreateUserProcessor;
import org.example.operations.processors.ShowAllUsersProcessor;
import org.example.services.AccountService;
import org.example.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class OperationProcessorsConfiguration {

    @Bean
    CreateUserProcessor createUserProcessor(
            Scanner scanner,
            UserService userService
    ) {
        return new CreateUserProcessor(scanner, userService);
    }

    @Bean
    ShowAllUsersProcessor showAllUsersProcessor(UserService userService) {
        return new ShowAllUsersProcessor(userService);
    }

    @Bean
    CreateAccountProcessor createAccountProcessor(
            Scanner scanner,
            UserService userService,
            AccountService accountService
    ) {
        return new CreateAccountProcessor(scanner, userService, accountService);
    }

}