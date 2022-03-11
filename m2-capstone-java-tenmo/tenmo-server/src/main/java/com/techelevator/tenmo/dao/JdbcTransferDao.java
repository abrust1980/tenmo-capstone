package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferDetail;
import com.techelevator.tenmo.model.TransferIncoming;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public boolean updatePendingTransfer(TransferStatus transferStatus, String userName, TransferDetail transfer){
        boolean isSuccesful = false;
        String sql = "UPDATE transfer " +
                "SET transfer_status_id = (SELECT transfer_status_id FROM transfer_status WHERE transfer_status_desc ILIKE ?) " +
                "WHERE transfer_id = ? " +
                "AND( account_from = (SELECT account_id FROM tenmo_user JOIN account ON account.user_id = tenmo_user.user_id WHERE username = ?)) " +
                "AND ( transfer_status_id = 1); ";
        String updateBalancesSql = "UPDATE account SET balance = balance + ? WHERE user_id = (SELECT user_id FROM tenmo_user WHERE username = ?) ;" +
                "UPDATE account SET balance = balance - ? WHERE user_id = (SELECT user_id FROM tenmo_user WHERE username = ?) ;";

        if (transferStatus.getStatus().equalsIgnoreCase("Approved")){
            //update both accounts and transfer
            try {
                jdbcTemplate.update(sql + updateBalancesSql, transferStatus.getStatus(), transferStatus.getTransferId(), userName,
                        transfer.getAmount(), transfer.getUserNameTo(), transfer.getAmount(), transfer.getUserNameFrom() );
                isSuccesful = true;
            } catch (DataAccessException e){
                return false;
            }
        }
        else if (transferStatus.getStatus().equalsIgnoreCase("Rejected")) {
            //update on transfer status
            try {
                jdbcTemplate.update(sql, transferStatus.getStatus(), transferStatus.getTransferId(), userName);
                isSuccesful = true;
            } catch (DataAccessException e){
                return false;
            }
        }
        return isSuccesful;
    }

    @Override
    public boolean addRequestTransfer(TransferIncoming transfer){
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES( " +
                "(SELECT transfer_type_id FROM transfer_type WHERE transfer_type_desc ILIKE ?), " +
                "(SELECT transfer_status_id FROM transfer_status WHERE transfer_status_desc ILIKE 'Pending'), " +
                "(SELECT account_id FROM account WHERE user_id = ?), " +
                "(SELECT account_id FROM account WHERE user_id = ?), " +
                "? );";
        try {
            jdbcTemplate.update(
                    sql,transfer.getType(),transfer.getUserIdFrom(), transfer.getUserIdTo(), transfer.getAmount());
        } catch (DataAccessException e){
            return false;
        }
        return true;
    }

    @Override
    public List<TransferDetail> getListOfTransfersForUser(String userName){
        String sql = "SELECT transfer_id, transfer_type_desc, transfer_status_desc, amount, " +
                "user_to.username AS username_to, user_from.username AS username_from FROM transfer " +
                "JOIN account acc_from ON acc_from.account_id = transfer.account_from " +
                "JOIN account acc_to ON acc_to.account_id = transfer.account_to " +
                "JOIN tenmo_user user_from ON acc_from.user_id = user_from.user_id " +
                "JOIN tenmo_user user_to ON acc_to.user_id = user_to.user_id " +
                "JOIN transfer_status ON transfer.transfer_status_id = transfer_status.transfer_status_id " +
                "JOIN transfer_type ON transfer.transfer_type_id = transfer_type.transfer_type_id " +
                "WHERE user_to.username = ? OR user_from.username = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userName, userName);
        List<TransferDetail> transfers = new ArrayList<>();
        while (rows.next()){
            transfers.add(createTransferDetailFromRowSet(rows));
        }
        return transfers;
    }

    @Override
    public List<TransferDetail> getListOfPendingTransferToUser(String userName){
        String sql = "SELECT transfer_id, transfer_type_desc, transfer_status_desc, amount, " +
                "user_to.username AS username_to, user_from.username AS username_from FROM transfer " +
                "JOIN account acc_from ON acc_from.account_id = transfer.account_from " +
                "JOIN account acc_to ON acc_to.account_id = transfer.account_to " +
                "JOIN tenmo_user user_from ON acc_from.user_id = user_from.user_id " +
                "JOIN tenmo_user user_to ON acc_to.user_id = user_to.user_id " +
                "JOIN transfer_status ON transfer.transfer_status_id = transfer_status.transfer_status_id " +
                "JOIN transfer_type ON transfer.transfer_type_id = transfer_type.transfer_type_id " +
                "WHERE transfer_status_desc = 'Pending' AND " +
                "( user_from.username = ? )";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userName);
        List<TransferDetail> transfers = new ArrayList<>();
        while (rows.next()){
            transfers.add(createTransferDetailFromRowSet(rows));
        }
        return transfers;
    }

    @Override
    public TransferDetail getTransferByIdForUser(String userName, long transferId){
        TransferDetail transfer = null;
        String sql = "SELECT transfer_id, transfer_type_desc, transfer_status_desc, amount, " +
                "user_to.username AS username_to, user_from.username AS username_from FROM transfer " +
                "JOIN account acc_from ON acc_from.account_id = transfer.account_from " +
                "JOIN account acc_to ON acc_to.account_id = transfer.account_to " +
                "JOIN tenmo_user user_from ON acc_from.user_id = user_from.user_id " +
                "JOIN tenmo_user user_to ON acc_to.user_id = user_to.user_id " +
                "JOIN transfer_status ON transfer.transfer_status_id = transfer_status.transfer_status_id " +
                "JOIN transfer_type ON transfer.transfer_type_id = transfer_type.transfer_type_id " +
                "WHERE transfer_id = ? AND " +
                "( user_from.username =? OR user_to.username =? )";

        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql,transferId, userName, userName );
        if (rows.next()){
            transfer = createTransferDetailFromRowSet(rows);
        }
        return transfer;
    }

    private TransferDetail createTransferDetailFromRowSet(SqlRowSet row){
        return new TransferDetail(
                row.getString("username_from"),
                row.getString("username_to"),
                row.getLong("transfer_id"),
                row.getDouble("amount"),
                row.getString("transfer_type_desc"),
                row.getString("transfer_status_desc")
        );
    }
}
