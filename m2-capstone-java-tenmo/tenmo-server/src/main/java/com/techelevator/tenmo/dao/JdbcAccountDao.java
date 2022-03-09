package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class JdbcAccountDao implements AccountDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Balance getAccountBalance(String username){
        String sql = "SELECT balance " +
                "FROM tenmo_user " +
                "JOIN account ON account.user_id = tenmo_user.user_id " +
                "WHERE username = ?";
        Double retrievedBalance = jdbcTemplate.queryForObject(sql, Double.class, username);
        Balance balance = new Balance();
        if (retrievedBalance != null) {
            balance.setBalance(retrievedBalance);
        }
        return balance;
    }
}
