package org.example;

import org.example.operations.ConsoleOperationType;
import org.example.operations.OperationCommandProcessor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class OperationsConsoleListener {

    private final Scanner scanner;
    private final Map<ConsoleOperationType, OperationCommandProcessor> processorMap;

    public OperationsConsoleListener(
            Scanner scanner,
            List<OperationCommandProcessor> commandProcessorList
    ) {
        this.scanner = scanner;
        this.processorMap = commandProcessorList
                .stream()
                .collect(
                        Collectors.toMap(
                                OperationCommandProcessor::getOperationType,
                                processor -> processor
                        )
                );;
    }

    public void start() {
        while (true) {
            ConsoleOperationType operationType = listenCommand();
            if (operationType == ConsoleOperationType.EXIT) {
                break;
            }
            processOperation(operationType);
        }
    }

    public ConsoleOperationType listenCommand() {
        while (true) {
            System.out.println("\nPlease enter one of operation type:\n1) USER_CREATE\n2) SHOW_ALL_USERS\n3) ACCOUNT_CREATE" +
                    "\n4) ACCOUNT_CLOSE\n5) ACCOUNT_DEPOSIT\n6) ACCOUNT_TRANSFER\n7) ACCOUNT_WITHDRAW\n8) EXIT");
            System.out.print("Enter the selected command: ");
            String choice = scanner.nextLine();
            try {
                return ConsoleOperationType.valueOf(choice);
            } catch (IllegalArgumentException e) {
                System.out.println("\nError: No such command found!");
            }
        }
    }

    public void processOperation(ConsoleOperationType operationType) {
        try {
            OperationCommandProcessor processor = processorMap.get(operationType);
            processor.processOperation();
        } catch(Exception e) {
            System.out.printf(
                    "\nError executing command %s: error=%s%n",
                    operationType,
                    e.getMessage()
            );
        }
    }

}