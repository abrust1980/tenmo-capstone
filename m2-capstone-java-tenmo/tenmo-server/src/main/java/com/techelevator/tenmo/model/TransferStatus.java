package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class TransferStatus {
    @NotEmpty
    private String status;
    @Min(value = 1, message = "transferId needs to be positive")
    private long transferId;

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
