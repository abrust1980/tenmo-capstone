package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.exception.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDetail;
import com.techelevator.tenmo.model.TransferIncoming;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {
    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao){
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path="/accounts/me/transfers", method = RequestMethod.GET)
    public List<TransferDetail> getTransfersForUser(Principal principal){
        return transferDao.getListOfTransfersForUser(principal.getName());
    }

    @RequestMapping(path="/accounts/me/transfers/{id}", method = RequestMethod.GET)
    public TransferDetail getTransferForUser(Principal principal, @PathVariable long id)
    throws TransferNotFoundException{
        TransferDetail transferDetail = transferDao.getTransferByIdForUser(principal.getName(), id);
        if (transferDetail == null){
            throw new TransferNotFoundException();
        }
        return transferDetail;
    }

    @RequestMapping(path="/transfers", method = RequestMethod.PUT)
    public boolean updateTransferStatus(Principal principal, @Valid @RequestBody TransferStatus transferStatus){
        boolean isSuccessful = false;
        TransferDetail transfer = transferDao.getTransferByIdForUser(principal.getName(), transferStatus.getTransferId());
        Account transferFromAccount = accountDao.getAccount(transfer.getUserNameFrom());
        Account transferToAccount = accountDao.getAccount(transfer.getUserNameTo());
        BigDecimal transferAmount = BigDecimal.valueOf(transfer.getAmount());

        if (transferStatus.getStatus().equalsIgnoreCase("Approved") ||
                transferStatus.getStatus().equalsIgnoreCase("Rejected")) {
            if (validateTransferBalanceAvailable(transferToAccount, transferFromAccount, transferAmount) ) {
                isSuccessful = transferDao.updatePendingTransfer(transferStatus, principal.getName(), transfer);
            } else {
                //not enough funds
            }

        }
//        } else {
//            //throw invalid status
//        }

        return isSuccessful;
    }

    @RequestMapping(path="/accounts/me/transfers/pending", method = RequestMethod.GET)
    public List<TransferDetail> getTransfersPendingForUser(Principal principal){
        return transferDao.getListOfPendingTransferToUser(principal.getName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="/transfers", method = RequestMethod.POST)
    public boolean postTransfer(@Valid @RequestBody TransferIncoming transferIncoming, Principal principal)
            throws TransferSenderException, TransferToSelfException, TransferTypeInvalidException,
            TransferInsufficientAmount, TransferInvalidAccountException {

        boolean success = false;
        Account transferToAccount = accountDao.getAccount(transferIncoming.getUserIdTo());
        Account transferFromAccount = accountDao.getAccount(transferIncoming.getUserIdFrom());

        if (transferFromAccount == null || transferToAccount == null){
            throw new TransferInvalidAccountException();
        }
        else {

            if (transferIncoming.getType().equalsIgnoreCase("Send")) {
                if (validateSendTransfer(transferToAccount, transferFromAccount, principal.getName())) {
                    if (validateTransferBalanceAvailable(transferToAccount, transferFromAccount, transferIncoming.getAmount())) {
                        success = transferDao.addSendingTransfer(transferIncoming);
                    } else {
                        throw new TransferInsufficientAmount();
                    }
                }
            }
            if (transferIncoming.getType().equalsIgnoreCase("Request")) {
                if (validateRequestTransfer(transferToAccount, transferFromAccount, principal.getName())) {
                    //add transfer to db
                    success = transferDao.addRequestTransfer(transferIncoming);
                }
            } else {
                //throw invalid transfer type
                throw new TransferTypeInvalidException();
            }
        }
        return success;
    }

    private boolean validateSendTransfer(Account transferToAccount, Account transferFromAccount, String userName)
    throws TransferSenderException, TransferToSelfException{
        boolean isValid = false;
        if ( transferFromAccount.getUserName().equals(userName) ){
            if (transferFromAccount.getUserId() != transferToAccount.getUserId()) {
                isValid = true;
            }else {
                throw new TransferToSelfException();
            }
        } else {
            throw new TransferSenderException();
        }
        return isValid;
    }

    private boolean validateRequestTransfer(Account transferToAccount, Account transferFromAccount, String userName)
            throws TransferSenderException, TransferToSelfException{
        boolean isValid = false;
        if ( transferToAccount.getUserName().equals(userName) ){
            if (transferFromAccount.getUserId() != transferToAccount.getUserId()) {
                isValid = true;
            }else {
                throw new TransferToSelfException();
            }
        } else {
            throw new TransferSenderException();
        }
        return isValid;
    }

    private boolean validateTransferBalanceAvailable(Account transferToAccount, Account transferFromAccount, BigDecimal amount){
        double transferToBalance = transferToAccount.getBalance();
        double transferFromBalance = transferFromAccount.getBalance();
        if (transferFromBalance >= amount.doubleValue()){
            return true;
        } else {
            return false;
        }

    }
}
