package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcAccountDao implements AccountDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccount(String userName){
        String sql = "SELECT account.user_id, username, account_id, tenmo_user.user_id, balance " +
                "FROM tenmo_user " +
                "JOIN account ON account.user_id = tenmo_user.user_id " +
                "WHERE username = ?";
        Account account = null;
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, userName);
        if (row.next()){
            account = createAccountFromRowSet(row);
        }
        return account;
    }

    @Override
    public Account getAccount(Long userId){
        String sql = "SELECT account.user_id, username, account_id, tenmo_user.user_id, balance " +
                "FROM tenmo_user " +
                "JOIN account ON account.user_id = tenmo_user.user_id " +
                "WHERE account.user_id = ?";
        Account account = null;
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, userId);
        if (row.next()){
            account = createAccountFromRowSet(row);
        }
        return account;
    }


    @Override
    public List<Account> listOtherAccounts(String userName){
        String sql = "SELECT account.user_id, username, account_id, tenmo_user.user_id, balance " +
                "FROM tenmo_user " +
                "JOIN account ON account.user_id = tenmo_user.user_id " +
                "WHERE username != ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userName);
        List<Account> listOfAccounts = new ArrayList<>();
        while (rows.next()){
            Account account = new Account();
            account.setUserName(rows.getString("username"));
            account.setUserId(rows.getLong("user_id"));
            listOfAccounts.add(account);
        }
        return listOfAccounts;
    }

    private Account createAccountFromRowSet(SqlRowSet row){
        Account account = new Account();
        account.setUserId(row.getLong("user_id"));
        account.setUserName(row.getString("username"));
        account.setBalance(row.getBigDecimal("balance"));
        return account;
    }
}
