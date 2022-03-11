package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

public class TransferIncoming {
    @Min(value = 1, message = "userIdFrom needs to be a positive number")
    private long userIdFrom;
    @Min(value = 1, message = "userIdTo needs to be a positive number")
    private long userIdTo;
    @Positive(message = "amount must be a positive number")
    private BigDecimal amount;
    @NotEmpty(message = "type should not be empty")
    private String type;

    public TransferIncoming() {}

    public TransferIncoming(long userIdFrom, long userIdTo, BigDecimal amount, String type) {
        this.userIdFrom = userIdFrom;
        this.userIdTo = userIdTo;
        this.amount = amount;
        this.type = type;
    }

    public long getUserIdFrom() {
        return userIdFrom;
    }

    public void setUserIdFrom(long userIdFrom) {
        this.userIdFrom = userIdFrom;
    }

    public long getUserIdTo() {
        return userIdTo;
    }

    public void setUserIdTo(long userIdTo) {
        this.userIdTo = userIdTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferIncoming that = (TransferIncoming) o;
        return userIdFrom == that.userIdFrom && userIdTo == that.userIdTo && Objects.equals(amount, that.amount) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userIdFrom, userIdTo, amount, type);
    }
}
