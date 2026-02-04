package org.example.operations.processors;

import org.example.models.Account;
import org.example.models.User;
import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import org.example.services.AccountService;
import org.example.services.UserService;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class CreateAccountProcessor implements OperationCommandProcessor {

    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public CreateAccountProcessor(Scanner scanner, UserService userService, AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.print("\nEnter the user id for which to create an account: ");
        int userId = Integer.parseInt(scanner.nextLine());
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No such user: id=%s".formatted(userId)));
        Account account = accountService.createAccount(userId);
        user.getAccountList().add(account);
        System.out.printf("\nNew account created with ID: %s for user: %s%n", account.getId(), user.getLogin());
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }

}