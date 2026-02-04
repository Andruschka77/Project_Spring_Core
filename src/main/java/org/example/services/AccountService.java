package org.example.services;

import org.example.models.Account;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private int idCounter;
    private final AccountProperties accountProperties;
    private final Map<Integer, Account> accountMap;

    public AccountService(AccountProperties accountProperties) {
        this.accountProperties = accountProperties;
        this.accountMap = new HashMap<>();
    }

    public Account createAccount(int userId) {
        idCounter++;
        Account account = new Account(idCounter, userId, accountProperties.getDefaultAmount());
        accountMap.put(account.getId(), account);
        return account;
    }

    public Optional<Account> findAccountById(int id) {
        return Optional.ofNullable(accountMap.get(id));
    }

    public List<Account> getAllUserAccounts(int userId) {
        return accountMap.values()
                .stream()
                .filter(x -> x.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public void depositAccount(int accountId, int moneyAmount) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(accountId)));
        account.setMoneyAmount(account.getMoneyAmount() + moneyAmount);
    }

    public void withdrawAccount(int accountId, int moneyAmount) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(accountId)));
        if (moneyAmount <= 0) {
            throw new IllegalArgumentException("Cannot withdraw negative money amount: id=%s"
                    .formatted(accountId));
        }
        if (account.getMoneyAmount() < moneyAmount) {
            throw new IllegalArgumentException(
                    "Cannot withdraw from account: id=%s, moneyAmount=%s, attemptedWithdraw=%s"
                            .formatted(accountId, account.getMoneyAmount(), moneyAmount)
            );
        }
        account.setMoneyAmount(account.getMoneyAmount() - moneyAmount);
    }

    public void transferAccount(int sendId, int receiveId, int moneyAmount) {
        Account sendAccount = findAccountById(sendId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(sendId)));
        Account receiveAccount = findAccountById(receiveId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(receiveId)));
        if (moneyAmount <= 0) {
            throw new IllegalArgumentException("Cannot transfer not positive amount: moneyAmount=%s"
                    .formatted(moneyAmount));
        }
        if (sendAccount.getMoneyAmount() < moneyAmount) {
            throw new IllegalArgumentException(
                    "Cannot withdraw from account: id=%s, moneyAmount=%s, attemptedWithdraw=%s"
                            .formatted(sendId, sendAccount.getMoneyAmount(), moneyAmount)
            );
        }
        int totalAmount = sendAccount.getUserId() != receiveAccount.getUserId()
                ? (int) (moneyAmount * (1 - accountProperties.getTransferCommission()))
                : moneyAmount;
        sendAccount.setMoneyAmount(sendAccount.getMoneyAmount() - moneyAmount);
        receiveAccount.setMoneyAmount(receiveAccount.getMoneyAmount() + totalAmount);
    }

    public Account closeAccount(int accountId) {
        Account accountToRemove = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(accountId)));
        List<Account> accountList = getAllUserAccounts(accountToRemove.getUserId());
        if (accountList.size() == 1) {
            throw new IllegalArgumentException("Cannot close the only one account");
        }
        Account accountToDeposit = accountList.stream()
                .filter(x -> x.getId() != accountId)
                .findFirst()
                .orElseThrow();
        accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + accountToRemove.getMoneyAmount());
        accountMap.remove(accountId);
        return accountToRemove;
    }

}