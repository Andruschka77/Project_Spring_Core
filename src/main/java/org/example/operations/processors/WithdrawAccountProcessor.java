package org.example.operations.processors;

import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import org.example.services.AccountService;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class WithdrawAccountProcessor implements OperationCommandProcessor {

    private final Scanner scanner;
    private final AccountService accountService;

    public WithdrawAccountProcessor(
            Scanner scanner,
            AccountService accountService
    ) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.print("\nEnter account ID to withdraw from: ");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter amount to withdraw: ");
        int moneyAmount = Integer.parseInt(scanner.nextLine());
        accountService.withdrawAccount(accountId, moneyAmount);
        System.out.printf("\nSuccessfully withdrawn amount=%s to accountId=%s%n", moneyAmount, accountId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }

}