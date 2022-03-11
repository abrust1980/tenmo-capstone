package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    private long userId;
    private String userName;
    private long accountId;
    private double balance;

    public Account(){}
    public Account(long userId, String userName, double balance) {
        this.userId = userId;
        this.userName = userName;
        this.balance = balance;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return userId == account.userId && accountId == account.accountId && Double.compare(account.balance, balance) == 0 && Objects.equals(userName, account.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, accountId, balance);
    }
}
