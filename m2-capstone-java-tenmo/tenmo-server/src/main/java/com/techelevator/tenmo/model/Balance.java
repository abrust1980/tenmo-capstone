package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Balance {
    private BigDecimal balance = new BigDecimal(0.0);

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public double getDoubleValue(){
        return balance.doubleValue();
    }

}
