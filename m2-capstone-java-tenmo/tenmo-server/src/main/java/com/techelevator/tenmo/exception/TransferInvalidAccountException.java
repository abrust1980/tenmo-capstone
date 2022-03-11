package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Invalid account.")
public class TransferInvalidAccountException extends Exception {
    public TransferInvalidAccountException(){ super("Invalid account.");}
}