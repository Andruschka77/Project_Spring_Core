package org.example.operations.processors;

import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import org.example.services.AccountService;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class DepositAccountProcessor implements OperationCommandProcessor {

    private final Scanner scanner;
    private final AccountService accountService;

    public DepositAccountProcessor(
            Scanner scanner,
            AccountService accountService
    ) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.print("\nEnter account ID: ");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter amount to deposit: ");
        int moneyAmount = Integer.parseInt(scanner.nextLine());
        accountService.depositAccount(accountId, moneyAmount);
        System.out.println("\nAmount " + moneyAmount + " deposited to account ID: " + accountId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }

}