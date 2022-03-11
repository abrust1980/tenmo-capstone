package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferDetail;
import com.techelevator.tenmo.model.TransferIncoming;
import com.techelevator.tenmo.model.TransferStatus;

import java.util.List;

public interface TransferDao {
    boolean addSendingTransfer(TransferIncoming transfer);
    public List<TransferDetail> getListOfTransfersForUser(String userName);
    TransferDetail getTransferByIdForUser(String userName, long transferId);
    boolean addRequestTransfer(TransferIncoming transfer);
    List<TransferDetail> getListOfPendingTransferToUser(String userName);
    boolean updatePendingTransfer(TransferStatus transferStatus, String userName, TransferDetail transfer);
}
