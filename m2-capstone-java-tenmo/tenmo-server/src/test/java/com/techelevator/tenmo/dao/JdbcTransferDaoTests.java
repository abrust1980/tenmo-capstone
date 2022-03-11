package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferIncoming;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcTransferDaoTests extends BaseDaoTests {
    private TransferDao transferDao;
    private static final TransferIncoming TRANSFER_1 = new TransferIncoming(1001,1002,new BigDecimal(200), "Send");
    private static final TransferIncoming TRANSFER_2 = new TransferIncoming(1002,1003,new BigDecimal(100), "Send");
    @Before
    public void setup(){
        JdbcTemplate template = new JdbcTemplate(dataSource);
        transferDao = new JdbcTransferDao(template);
    }

    @Test
    public void addSendingTransfer_has_expected_values_when_retrieved(){

    }
}
