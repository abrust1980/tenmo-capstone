package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.BAD_REQUEST, reason = "Invalid transfer sender.")
public class TransferSenderException extends Exception{

    public TransferSenderException() {
        super("Invalid transfer sender.");
    }

}