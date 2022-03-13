package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.OutboundTransfer;
import com.techelevator.tenmo.model.TransferDetail;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private RestTemplate restTemplate = new RestTemplate();
    private String baseUrl = "";
    private AuthenticatedUser user;


    public TransferService(String baseUrl) {
        this.baseUrl = baseUrl ;
    }

    public void setUser(AuthenticatedUser user) {
        this.user = user;
    }

    public boolean newTransfer(OutboundTransfer transfer){
      boolean isSuccessful = false;
        try {
          restTemplate.exchange(baseUrl + "transfers/", HttpMethod.POST, makeTransferEntity(transfer), Void.class);
            isSuccessful = true;
      } catch (RestClientResponseException | ResourceAccessException e) {
          BasicLogger.log(e.getMessage());

      }

        return isSuccessful;
    }

    public TransferDetail[] getAllTransfers() {
        TransferDetail[] transferDetails = null;
        try {
            transferDetails = restTemplate.exchange(baseUrl + "accounts/me/transfers", HttpMethod.GET,
                    makeAuthEntity(), TransferDetail[].class).getBody();

        } catch (RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        } return transferDetails;
    }

    public TransferDetail[] getPendingTransfers() {
        TransferDetail[] transferDetails = null;
        try {
            transferDetails = restTemplate.exchange(baseUrl + "accounts/me/transfers/pending", HttpMethod.GET,
                    makeAuthEntity(), TransferDetail[].class).getBody();

        } catch (RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        } return transferDetails;
    }

    public boolean updatePendingTransfer(TransferStatus transferStatus) {
        boolean isSuccessful = false;
        try {
            Boolean success = restTemplate.exchange(baseUrl + "transfers", HttpMethod.PUT,
                    makeTransferEntity(transferStatus), Boolean.class).getBody();
            if (success != null) {
                isSuccessful = success;
            }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return isSuccessful;
    }

    private HttpEntity<OutboundTransfer> makeTransferEntity(OutboundTransfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(transfer, headers);
    }
    private HttpEntity<TransferStatus> makeTransferEntity(TransferStatus transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(transfer, headers);
    }
    private HttpEntity<Void> makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        return new HttpEntity<>(headers);
    }
}
