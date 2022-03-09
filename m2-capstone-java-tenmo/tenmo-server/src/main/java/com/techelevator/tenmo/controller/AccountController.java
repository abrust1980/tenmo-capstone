package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Balance;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/account")
public class AccountController {
    private AccountDao accountDao;

    public AccountController(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    @RequestMapping(path="/balance", method= RequestMethod.GET)
    public Balance getAccountBalance(Principal principal){
        return accountDao.getAccountBalance(principal.getName());
    }

}
