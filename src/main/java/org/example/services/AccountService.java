package org.example.services;

import org.example.TransactionHelper;
import org.example.models.Account;
import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountProperties accountProperties;
    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;

    public AccountService(
            AccountProperties accountProperties,
            TransactionHelper transactionHelper,
            SessionFactory sessionFactory
    ) {
        this.accountProperties = accountProperties;
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }

    public Account createAccount(Long userId) {
        return transactionHelper.executeInTransaction(session -> {
            User user = session.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                    .setParameter("id", userId)
                    .uniqueResult();
            Account account = new Account(accountProperties.getDefaultAmount(), user);
            user.getAccountList().add(account);
            session.persist(account);
            return account;
        });
    }

    public Optional<Account> findAccountById(Long accountId) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Account.class, accountId));
        }
    }
//
//    public List<Account> getAllUserAccounts(int userId) {
//        return accountMap.values()
//                .stream()
//                .filter(x -> x.getUserId() == userId)
//                .collect(Collectors.toList());
//    }
//
//    public void depositAccount(int accountId, int moneyAmount) {
//        Account account = findAccountById(accountId)
//                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(accountId)));
//        account.setMoneyAmount(account.getMoneyAmount() + moneyAmount);
//    }
//
//    public void withdrawAccount(int accountId, int moneyAmount) {
//        Account account = findAccountById(accountId)
//                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(accountId)));
//        if (moneyAmount <= 0) {
//            throw new IllegalArgumentException("Cannot withdraw negative money amount: id=%s"
//                    .formatted(accountId));
//        }
//        if (account.getMoneyAmount() < moneyAmount) {
//            throw new IllegalArgumentException(
//                    "Cannot withdraw from account: id=%s, moneyAmount=%s, attemptedWithdraw=%s"
//                            .formatted(accountId, account.getMoneyAmount(), moneyAmount)
//            );
//        }
//        account.setMoneyAmount(account.getMoneyAmount() - moneyAmount);
//    }
//
//    public void transferAccount(int sendId, int receiveId, int moneyAmount) {
//        Account sendAccount = findAccountById(sendId)
//                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(sendId)));
//        Account receiveAccount = findAccountById(receiveId)
//                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(receiveId)));
//        if (moneyAmount <= 0) {
//            throw new IllegalArgumentException("Cannot transfer not positive amount: moneyAmount=%s"
//                    .formatted(moneyAmount));
//        }
//        if (sendAccount.getMoneyAmount() < moneyAmount) {
//            throw new IllegalArgumentException(
//                    "Cannot withdraw from account: id=%s, moneyAmount=%s, attemptedWithdraw=%s"
//                            .formatted(sendId, sendAccount.getMoneyAmount(), moneyAmount)
//            );
//        }
//        int totalAmount = sendAccount.getUserId() != receiveAccount.getUserId()
//                ? (int) (moneyAmount * (1 - accountProperties.getTransferCommission()))
//                : moneyAmount;
//        sendAccount.setMoneyAmount(sendAccount.getMoneyAmount() - moneyAmount);
//        receiveAccount.setMoneyAmount(receiveAccount.getMoneyAmount() + totalAmount);
//    }
//
//    public Account closeAccount(int accountId) {
//        Account accountToRemove = findAccountById(accountId)
//                .orElseThrow(() -> new IllegalArgumentException("No such account: id=%s".formatted(accountId)));
//        List<Account> accountList = getAllUserAccounts(accountToRemove.getUserId());
//        if (accountList.size() == 1) {
//            throw new IllegalArgumentException("Cannot close the only one account");
//        }
//        Account accountToDeposit = accountList.stream()
//                .filter(x -> x.getId() != accountId)
//                .findFirst()
//                .orElseThrow();
//        accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + accountToRemove.getMoneyAmount());
//        accountMap.remove(accountId);
//        return accountToRemove;
//    }

}