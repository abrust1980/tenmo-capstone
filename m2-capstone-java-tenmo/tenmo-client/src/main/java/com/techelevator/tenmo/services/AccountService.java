package com.techelevator.tenmo.services;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Balance;
import org.springframework.web.client.RestTemplate;



public class AccountService {
    private RestTemplate restTemplate = new RestTemplate();

    private String baseUrl = "";
    private AuthenticatedUser user;

    public AccountService(String baseUrl) {
        this.baseUrl = baseUrl + "account/";
    }

    public void setUser(AuthenticatedUser user) {
        this.user = user;
    }

    public Balance getAccountBalance() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user.getToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(baseUrl + "balance", HttpMethod.GET, entity, Balance.class).getBody();
    }


}
