package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferIncoming;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferDao implements TransferDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean addSendingTransfer(TransferIncoming transfer){
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES( " +
                "(SELECT transfer_type_id FROM transfer_type WHERE transfer_type_desc ILIKE ?), " +
                "(SELECT transfer_status_id FROM transfer_status WHERE transfer_status_desc ILIKE 'Approved'), " +
                "(SELECT account_id FROM account WHERE user_id = ?), " +
                "(SELECT account_id FROM account WHERE user_id = ?), " +
                "? );" +
                "UPDATE account SET balance = balance + ? WHERE user_id = ? ;" +
                "UPDATE account SET balance = balance - ? WHERE user_id = ? ;";
        try {
            jdbcTemplate.update(
                    sql,transfer.getType(),transfer.getUserIdFrom(), transfer.getUserIdTo(), transfer.getAmount(),
                    transfer.getAmount(), transfer.getUserIdTo(), transfer.getAmount(), transfer.getUserIdFrom());
        } catch (DataAccessException e){
            return false;
        }
        return true;
    }
}
