package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.BAD_REQUEST, reason = "Invalid transfer status.")
public class TransferStatusInvalidException extends Exception {
    public TransferStatusInvalidException(){
        super("Invalid transfer status.");
    }

}
