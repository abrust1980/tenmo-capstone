package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;


public interface AccountDao {
    Account getAccount(String userName);
    Account getAccount(Long userId);
    List<Account> listOtherAccounts(String userName);
}
