package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.BAD_REQUEST, reason = "Can't send more money than is in your account")
public class TransferInsufficientAmount extends Exception{
    public TransferInsufficientAmount() {
        super("Can't send more money than is in your account.");
    }
}
