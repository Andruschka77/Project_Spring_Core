package org.example.services;

import org.example.models.Account;
import org.example.models.User;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private int idCounter;
    private final Map<Integer, User> userMap;
    private final AccountService accountService;
    private final Set<String> takenLogins;

    public UserService(AccountService accountService) {
        this.accountService = accountService;
        this.userMap = new HashMap<>();
        takenLogins = new HashSet<>();
    }

    public User createUser(String login) {
        if (takenLogins.contains(login)) {
            throw new IllegalArgumentException("Such a login already exists!");
        }
        takenLogins.add(login);
        idCounter++;
        User user = new User(idCounter, login, new ArrayList<>());
        Account account = accountService.createAccount(idCounter);
        user.setAccountList(account);
        userMap.put(user.getId(), user);
        return user;
    }

    public List<User> getAllUsers() {
        return userMap.values().stream().collect(Collectors.toList());
    }

    public Optional<User> findUserById(int userId) {
        return Optional.ofNullable(userMap.get(userId));
    }

}