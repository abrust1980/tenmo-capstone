package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDetail;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public void printBalance(double balance) {
        System.out.printf("Your current account balance is: $%.2f", balance );
    }

    public void printTransferMenu(Account[] accounts){
        printMenuBorder();
        System.out.println("Users");
        System.out.println("ID          Name");
        printMenuBorder();
        for(Account account : accounts){
            System.out.printf("%-11s %s", account.getUserId(), account.getUserName());
            System.out.println();
        }
        printMenuSubBorder();
    }

    private void printMenuBorder(){
        System.out.println("-------------------------------------------");
    }

    private void printMenuSubBorder() {
        System.out.println("---------");
    }

    public void printViewTransfersMenu(TransferDetail[] transferDetails, String userName){
        printMenuBorder();
        System.out.println("Transfers");
        System.out.println("ID          From/To                 Amount");
        printMenuBorder();
        for(TransferDetail transfer : transferDetails) {
            System.out.printf( "%-11s %-22s %s", transfer.getTransferId(), transfer.getFromOrTo(userName), transfer.amountToString());
            System.out.println();
        }
    }

    public void printPendingTransferMenu(TransferDetail[] transferDetails, String userName){
        printMenuBorder();
        System.out.println("Pending Transfers");
        System.out.println("ID          To                 Amount");
        printMenuBorder();
        for(TransferDetail transfer : transferDetails) {
            System.out.printf( "%-11s %-22s %s", transfer.getTransferId(), transfer.getUserNameTo(), transfer.amountToString());
            System.out.println();
        }
        printMenuSubBorder();
    }
    public void printApproveOrRejectMenu(){
        System.out.println("1: Approve");
        System.out.println("2: Reject");
        System.out.println("3: Don't approve or reject");
        printMenuSubBorder();
    }
    public void printTransferDetail(TransferDetail transferDetail) {
       printMenuBorder();
        System.out.println("Transfer Details");
        printMenuBorder();
        System.out.println(transferDetail);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

}
