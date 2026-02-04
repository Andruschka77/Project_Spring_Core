package org.example.operations.processors;

import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import org.example.services.AccountService;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class TransferAccountProcessor implements OperationCommandProcessor {

    private final Scanner scanner;
    private final AccountService accountService;

    public TransferAccountProcessor(
            Scanner scanner,
            AccountService accountService
    ) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void processOperation() {
        System.out.print("\nEnter source account ID: ");
        int sendId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter target account ID: ");
        int receiveId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter amount to transfer: ");
        int moneyAmount = Integer.parseInt(scanner.nextLine());
        accountService.transferAccount(sendId, receiveId, moneyAmount);
        System.out.printf("\nAmount %s transferred from account ID %s to account ID %s%n",
                moneyAmount, sendId, receiveId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }

}