package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private long userId;
    private String userName;
    private long accountId;
    private Balance balance = new Balance();


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

    public double getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance.setBalance(balance);
    }
}
