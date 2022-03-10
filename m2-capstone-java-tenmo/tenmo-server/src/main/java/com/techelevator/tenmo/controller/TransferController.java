package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.exception.TransferInsufficientAmount;
import com.techelevator.tenmo.exception.TransferSenderException;
import com.techelevator.tenmo.exception.TransferToSelfException;
import com.techelevator.tenmo.exception.TransferTypeInvalidException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.TransferIncoming;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/transfers")
public class TransferController {
    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao){
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="", method = RequestMethod.POST)
    public boolean postTransfer(@Valid @RequestBody TransferIncoming transferIncoming,
                             Principal principal) throws
            TransferSenderException, TransferToSelfException, TransferTypeInvalidException, TransferInsufficientAmount {
        boolean success = false;
        Account transferToAccount = accountDao.getAccount(transferIncoming.getUserIdTo());
        Account transferFromAccount = accountDao.getAccount(transferIncoming.getUserIdFrom());

        if (transferIncoming.getType().equalsIgnoreCase("Send")) {
            if ( transferFromAccount.getUserName().equals(principal.getName()) ){
                if (transferFromAccount.getUserId() != transferToAccount.getUserId()){
                    //do send
                    boolean isTransferValid =
                            validateTransfer(transferToAccount, transferFromAccount, transferIncoming.getAmount());
                    if (isTransferValid){
                        success = transferDao.addSendingTransfer(transferIncoming);
                    } else {
                        throw new TransferInsufficientAmount();
                    }

                } else {
                    //throw can't send money to yourself
                    throw new TransferToSelfException();
                }
            } else {
                // throw you must be the sender
                throw new TransferSenderException();
            }
        }
        else {
            //throw invalid transfer type
            throw new TransferTypeInvalidException();
        }
        return success;
    }

    private boolean validateTransfer(Account transferToAccount, Account transferFromAccount, BigDecimal amount){
        Balance transferToBalance = transferToAccount.getBalance();
        Balance transferFromBalance = transferFromAccount.getBalance();
        if (transferFromBalance.getDoubleValue() >= amount.doubleValue()){
            return true;
        } else {
            return false;
        }

    }
}
