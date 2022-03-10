package com.techelevator.tenmo.services;
import com.techelevator.tenmo.model.Account;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


public class AccountService {
    private RestTemplate restTemplate = new RestTemplate();

    private String baseUrl = "";
    private AuthenticatedUser user;

    public AccountService(String baseUrl) {
        this.baseUrl = baseUrl + "accounts/";
    }

    public void setUser(AuthenticatedUser user) {
        this.user = user;
    }

    public Balance getAccountBalance() {
        Balance accountBalance = null;
        try{
            accountBalance = restTemplate.exchange(baseUrl + "me/balance", HttpMethod.GET, makeAuthEntity(), Balance.class).getBody();
        }
        catch (RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return accountBalance;
    }

    public Account[] getOtherAccounts() {
        Account[] accounts = null;
        try {
            accounts = restTemplate.exchange(baseUrl, HttpMethod.GET, makeAuthEntity(), Account[].class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return accounts;
    }

    private HttpEntity<Void> makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        return new HttpEntity<>(headers);
    }


}
