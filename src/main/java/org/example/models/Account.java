package org.example.models;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_moneyAmount", nullable = false)
    private int moneyAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Account() {}

    public Account(int moneyAmount, User user) {
        this.moneyAmount = moneyAmount;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{id=" + id + ", userId=" + user.getId() + ", moneyAmount=" + moneyAmount + "}";
    }

    public Long getId() {
        return id;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public Long getUserId() {
        return user.getId();
    }

}