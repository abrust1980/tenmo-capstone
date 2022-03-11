package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Objects;

public class JdbcAccountDaoTests extends BaseDaoTests {

    private AccountDao accountDao;
    private static final Account ACCOUNT_1 = new Account(1001,"testUserOne", 1000);
    private static final Account ACCOUNT_2 = new Account(1002,"testUserTwo", 1000);
    private static final Account ACCOUNT_3 = new Account(1003,"testUserThree", 1000);
    private static final Account ACCOUNT_4 = new Account(1004,"testUserFour",  1000);

    @Before
    public void setup(){
        JdbcTemplate template = new JdbcTemplate(dataSource);
        accountDao = new JdbcAccountDao(template);
    }

    @Test
    public void getAccount_by_username_returns_correct_account(){
        Account account1 = accountDao.getAccount("testUserOne");
        Assert.assertEquals(ACCOUNT_1, account1);

        Account account2 = accountDao.getAccount("testUserTwo");
        Assert.assertEquals(ACCOUNT_2, account2);
    }

    @Test
    public void getAccount_by_userid_returns_correct_account(){
        Account account3 = accountDao.getAccount(1003L);
        Assert.assertEquals(ACCOUNT_3, account3);

        Account account4 = accountDao.getAccount(1004L);
        Assert.assertEquals(ACCOUNT_4, account4);
    }

    @Test
    public void listOtherAccounts_only_gets_other_accounts(){
        List<Account> accounts = accountDao.listOtherAccounts("testUserTwo");
        Assert.assertEquals(3, accounts.size());
        for(Account account : accounts){
            Assert.assertNotEquals(ACCOUNT_2, account);
        }
    }
}
