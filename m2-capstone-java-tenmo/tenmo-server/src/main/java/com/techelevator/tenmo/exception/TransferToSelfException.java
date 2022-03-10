package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.BAD_REQUEST, reason = "Can't transfer money to yourself")
public class TransferToSelfException extends Exception{

    public TransferToSelfException() {
        super("Can't transfer money to yourself");
    }

}
