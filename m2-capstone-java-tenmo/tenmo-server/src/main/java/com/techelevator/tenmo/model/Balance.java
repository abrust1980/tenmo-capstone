package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Balance {
    private double balance;

    public double getBalance() {
        return balance;
    }
    public Balance(){}

    public Balance(double balance) {
        this.balance = balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
