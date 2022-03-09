package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;

public interface AccountDao {
    Balance getAccountBalance(String username);
}
