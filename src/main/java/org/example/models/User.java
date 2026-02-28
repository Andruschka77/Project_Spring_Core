package org.example.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_login", unique = true, nullable = false)
    private String login;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Account> accountList = new ArrayList<>();

    public User() {}

    public User(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login='" + login + "', accountList=" + accountList + "}";
    }

    public String getLogin() {
        return login;
    }

    public Long getId() {
        return id;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

}