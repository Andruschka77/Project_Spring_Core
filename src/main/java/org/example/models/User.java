package org.example.models;

import java.util.List;
import java.util.stream.Collectors;

public class User {

    private final int id;
    private final String login;
    private final List<Account> accountList;

    public User(int id, String login, List<Account> accountList) {
        this.id = id;
        this.login = login;
        this.accountList = accountList;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login='" + login + "', accountList=" +
                accountList.stream().map(Object::toString).collect(Collectors.toList())
                + "}";
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

}