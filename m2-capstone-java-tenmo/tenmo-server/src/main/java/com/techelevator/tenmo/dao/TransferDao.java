package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferIncoming;

public interface TransferDao {
    boolean addSendingTransfer(TransferIncoming transfer);
}
