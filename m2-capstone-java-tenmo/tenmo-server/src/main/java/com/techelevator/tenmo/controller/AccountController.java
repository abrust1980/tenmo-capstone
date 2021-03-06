package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountDao accountDao;

    public AccountController(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    @RequestMapping(path="/me/balance", method= RequestMethod.GET)
    public Balance getAccountBalance(Principal principal){
        Account currentAccount = accountDao.getAccount(principal.getName());
        Balance balance = new Balance(currentAccount.getBalance());
        return balance;
    }

    @RequestMapping(path="", method = RequestMethod.GET)
    public List<Account> getOtherAccounts(Principal principal){
        return accountDao.listOtherAccounts(principal.getName());
    }

}
