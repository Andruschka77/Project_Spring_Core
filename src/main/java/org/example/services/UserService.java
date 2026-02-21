package org.example.services;

import org.example.TransactionHelper;
import org.example.models.Account;
import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {

    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;
    private final AccountProperties accountProperties;
    private final Set<String> takenLogins = new HashSet<>();

    public UserService(
            TransactionHelper transactionHelper,
            SessionFactory sessionFactory,
            AccountProperties accountProperties
    ) {
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
        this.accountProperties = accountProperties;
    }

    public User createUser(String login) {
        if (takenLogins.contains(login)) {
            throw new IllegalArgumentException("Such a login already exists!");
        }
        takenLogins.add(login);
        User user = new User(login);
        Account account = new Account(accountProperties.getDefaultAmount(), user);
        user.getAccountList().add(account);
        return transactionHelper.executeInTransaction(session -> {
            session.persist(user);
            session.persist(account);
            return user;
        });
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("""
                    SELECT DISTINCT u FROM User u
                    LEFT JOIN FETCH u.accountList
                    ORDER BY u.id ASC
                    """, User.class)
                    .list();
        }
    }

    public Optional<User> findUserById(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(User.class, userId));
        }
    }

}