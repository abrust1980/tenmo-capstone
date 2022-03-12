package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.BAD_REQUEST, reason = "Insufficient amount")
public class TransferInsufficientAmount extends Exception{
    public TransferInsufficientAmount() {
        super("Insufficient amount");
    }
}
