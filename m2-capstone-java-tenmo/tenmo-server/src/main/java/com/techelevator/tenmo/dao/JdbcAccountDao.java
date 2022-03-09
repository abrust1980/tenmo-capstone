package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.RowSet;


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

    private Account createAccountFromRowSet(SqlRowSet row){
        Account account = new Account();
        account.setUserId(row.getLong("user_id"));
        account.setUserName(row.getString("username"));
        Balance balance = new Balance();
        balance.setBalance(row.getDouble("balance"));
        account.setBalance(balance);
        return account;
    }
}
