package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDetail;
import com.techelevator.tenmo.model.TransferIncoming;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDaoTests extends BaseDaoTests {
    private TransferDao transferDao;
    private AccountDao accountDao;
    private static final TransferIncoming TRANSFER_1 = new TransferIncoming(1001,1002,new BigDecimal(200), "Send");
    private static final TransferIncoming TRANSFER_2 = new TransferIncoming(1002,1003,new BigDecimal(200), "Request");
    private static final TransferDetail TRANSFER_DETAIL_1 = new TransferDetail("testUserOne", "testUserTwo", 3001, 200, "Send", "Approved");
    private static final TransferDetail TRANSFER_DETAIL_2 = new TransferDetail("testUserTwo", "testUserThree", 3003, 200, "Request", "Pending");
    @Before
    public void setup(){
        JdbcTemplate template = new JdbcTemplate(dataSource);
        transferDao = new JdbcTransferDao(template);
        accountDao = new JdbcAccountDao(template);
    }

//    @Test
//    public void addSendingTransfer_has_expected_values_when_retrieved(){
//        transferDao.addSendingTransfer(TRANSFER_1);
//        TransferDetail retrievedTransfer = transferDao.getTransferByIdForUser("testUserOne", 3001);
//        Assert.assertEquals(TRANSFER_DETAIL_1, retrievedTransfer);
//    }

    @Test
    public void addSendingTransfer_changes_account_balances_correctly(){
        transferDao.addSendingTransfer(TRANSFER_1);
        double fromAccountBalance = accountDao.getAccount(1001L).getBalance();
        double toAccountBalance = accountDao.getAccount(1002L).getBalance();
        Assert.assertEquals(800.0, fromAccountBalance, 0.9);
        Assert.assertEquals(1200.0, toAccountBalance, 0.9);
    }

//    @Test
//    public void addRequestTransfer_has_expected_values_when_retrieved(){
//        transferDao.addRequestTransfer(TRANSFER_2);
//        TransferDetail retrievedTransfer = transferDao.getTransferByIdForUser("testUserThree", 3003);
//        Assert.assertEquals(TRANSFER_DETAIL_2, retrievedTransfer);
//    }

    @Test
    public void addRequestTransfer_does_not_change_account_balances(){
        transferDao.addRequestTransfer(TRANSFER_2);
        double fromAccountBalance = accountDao.getAccount(1002L).getBalance();
        double toAccountBalance = accountDao.getAccount(1003L).getBalance();
        Assert.assertEquals(1000.0, fromAccountBalance, 0.9);
        Assert.assertEquals(1000.0, toAccountBalance, 0.9);
    }

    @Test
    public void getListOfPendingTransferToUser_has_correct_size(){
        transferDao.addRequestTransfer(TRANSFER_2);
        transferDao.addRequestTransfer(TRANSFER_2);
        List<TransferDetail> transfers = transferDao.getListOfPendingTransferToUser("testUserTwo");
        Assert.assertEquals(2,transfers.size());
    }

    @Test
    public void getListOfTransfersForUser_has_all_transfers(){
        transferDao.addRequestTransfer(TRANSFER_2);
        transferDao.addRequestTransfer(TRANSFER_2);
        transferDao.addSendingTransfer(TRANSFER_1);
        List<TransferDetail> transfers = transferDao.getListOfTransfersForUser("testUserTwo");
        Assert.assertEquals(3,transfers.size());
    }
}
