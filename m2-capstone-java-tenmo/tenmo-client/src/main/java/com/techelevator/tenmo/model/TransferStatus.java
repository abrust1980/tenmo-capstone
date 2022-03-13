package com.techelevator.tenmo.model;

public class TransferStatus {

    private String status;

    private long transferId;

    public TransferStatus(){};

    public TransferStatus(String status, long transferId) {
        this.status = status;
        this.transferId = transferId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }


}
