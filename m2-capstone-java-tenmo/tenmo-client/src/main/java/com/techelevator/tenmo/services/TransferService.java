package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.OutboundTransfer;
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
        this.baseUrl = baseUrl + "transfers/";
    }

    public void setUser(AuthenticatedUser user) {
        this.user = user;
    }

    public boolean newTransfer(OutboundTransfer transfer){
      boolean isSuccessful = false;
        try {
          restTemplate.exchange(baseUrl, HttpMethod.POST, makeTransferEntity(transfer), Void.class);
            isSuccessful = true;
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
}
