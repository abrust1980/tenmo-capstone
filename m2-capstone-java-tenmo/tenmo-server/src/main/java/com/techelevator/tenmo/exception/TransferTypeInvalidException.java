package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.BAD_REQUEST, reason = "Invalid transfer type.")
public class TransferTypeInvalidException extends Exception {
    public TransferTypeInvalidException(){
        super("Invalid transfer type.");
    }

}
