package com.techelevator.tenmo.model;

import java.util.Objects;

public class TransferDetail {
    private String userNameFrom;
    private String userNameTo;
    private long transferId;
    private double amount;
    private String transferType;
    private String transferStatus;

    public TransferDetail(){};

    public TransferDetail(String userNameFrom, String userNameTo, long transferId, double amount, String transferType, String transferStatus) {
        this.userNameFrom = userNameFrom;
        this.userNameTo = userNameTo;
        this.transferId = transferId;
        this.amount = amount;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferDetail that = (TransferDetail) o;
        return transferId == that.transferId && Double.compare(that.amount, amount) == 0 && Objects.equals(userNameFrom, that.userNameFrom) && Objects.equals(userNameTo, that.userNameTo) && Objects.equals(transferType, that.transferType) && Objects.equals(transferStatus, that.transferStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNameFrom, userNameTo, transferId, amount, transferType, transferStatus);
    }

    public String amountToString(){
        return String.format("$ %.2f", amount);
    }

    public String getFromOrTo(String userName) {
        String userNameFromOrTo = "";
        if(userName.equals(userNameFrom)) {
            userNameFromOrTo = "To: " + userNameTo;
            return userNameFromOrTo;
        } else {
            userNameFromOrTo = "From: " + userNameFrom;
            return userNameFromOrTo;
        }
    }

    public String getUserNameFrom() {
        return userNameFrom;
    }

    public void setUserNameFrom(String userNameFrom) {
        this.userNameFrom = userNameFrom;
    }

    public String getUserNameTo() {
        return userNameTo;
    }

    public void setUserNameTo(String userNameTo) {
        this.userNameTo = userNameTo;
    }

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(long transferId) {
        this.transferId = transferId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    @Override
    public String toString() {
        return "Id: " + transferId +"\n" +
                "From: " + userNameFrom +"\n" +
                "To: " + userNameTo +"\n" +
                "Type: " + transferType +"\n" +
                "Status: " + transferStatus +"\n" +
                "Amount: $" + amount;
    }
}
