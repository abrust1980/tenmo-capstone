package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

import java.math.BigDecimal;
import java.net.http.HttpHeaders;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private AuthenticatedUser currentUser;
    private TransferService transferService = new TransferService(API_BASE_URL);

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            accountService.setUser(currentUser);
            transferService.setUser(currentUser);
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        Balance balance = accountService.getAccountBalance();
        consoleService.printBalance(balance.getBalance());
		
	}

	private void viewTransferHistory() {
		TransferDetail[] transferDetails = transferService.getAllTransfers();
        consoleService.printViewTransfersMenu(transferDetails, currentUser.getUser().getUsername());
        int transferId = consoleService.promptForInt("Please enter transfer ID to view details (0 to cancel): ");
		if (transferId!= 0) {
            for(TransferDetail currentTransferDetail : transferDetails) {
                if(transferId == currentTransferDetail.getTransferId()) {
                    consoleService.printTransferDetail(currentTransferDetail);
                }
            }

        }
	}

	private void viewPendingRequests() {
        TransferDetail[] transferDetails = transferService.getPendingTransfers();
        consoleService.printPendingTransferMenu(transferDetails, currentUser.getUser().getUsername());
        int transferId = consoleService.promptForInt("Please enter transfer ID to approve/reject (0 to cancel): ");
        if (transferId != 0){
            for(TransferDetail currentTransferDetail : transferDetails) {
                if(transferId == currentTransferDetail.getTransferId()) {
                    consoleService.printApproveOrRejectMenu();
                    int menuChoice = consoleService.promptForMenuSelection("Please choose an option: ");
                    if (menuChoice == 1){
                        //approve
                        TransferStatus transfer = new TransferStatus("Approved", currentTransferDetail.getTransferId());
                        transferService.updatePendingTransfer(transfer);
                    } else if ( menuChoice == 2){
                        //reject
                        TransferStatus transfer = new TransferStatus("Rejected", currentTransferDetail.getTransferId());
                        transferService.updatePendingTransfer(transfer);
                    }
                    }
                }
        }
		
	}

	private void sendBucks() {
        Account[] accounts = accountService.getOtherAccounts();
		consoleService.printTransferMenu(accounts);
		int transferToId = consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel): ");
        if (transferToId != 0){
            BigDecimal transferAmount = consoleService.promptForBigDecimal("Enter amount: ");
            OutboundTransfer outboundTransfer = new OutboundTransfer();
            outboundTransfer.setAmount(transferAmount);
            outboundTransfer.setUserIdFrom(currentUser.getUser().getId());
            outboundTransfer.setUserIdTo(transferToId);
            outboundTransfer.setType("Send");
            boolean isSuccessful = transferService.newTransfer(outboundTransfer);
            if (!isSuccessful) {
                consoleService.printErrorMessage();
            }
        }

	}


	private void requestBucks() {
        Account[] accounts = accountService.getOtherAccounts();
        consoleService.printTransferMenu(accounts);
        int transferToId = consoleService.promptForInt("Enter ID of user you are requesting from (0 to cancel): ");
        if (transferToId != 0){
            BigDecimal transferAmount = consoleService.promptForBigDecimal("Enter amount: ");
            OutboundTransfer outboundTransfer = new OutboundTransfer();
            outboundTransfer.setAmount(transferAmount);
            outboundTransfer.setUserIdTo(currentUser.getUser().getId());
            outboundTransfer.setUserIdFrom(transferToId);
            outboundTransfer.setType("Request");
            boolean isSuccessful = transferService.newTransfer(outboundTransfer);
            if (!isSuccessful) {
                consoleService.printErrorMessage();
            }
        }
		
	}

}
