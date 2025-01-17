package org.example.service;

import org.example.dto.AccessTokenResponse;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class AccessTokenManager {

    private AccessTokenResponse currentAccessTokenResponse;
    private long tokenExpirationTime;

    public boolean isTokenExpired() {
        return currentAccessTokenResponse == null || System.currentTimeMillis() >= tokenExpirationTime;
    }

    public AccessTokenResponse fetchAccessToken(String tokenUrl) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> requestEntity = new HttpEntity<>(
                    "grant_type=client_credentials&client_id=pf6a9YiXSsHWdcOUULNucZePBdANQwLl&client_secret=Pyh6XLhmb6PW5jrY",
                    headers
            );

            ResponseEntity<AccessTokenResponse> response = restTemplate.postForEntity(tokenUrl, requestEntity, AccessTokenResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                currentAccessTokenResponse = response.getBody();
                tokenExpirationTime = System.currentTimeMillis() + (currentAccessTokenResponse.getExpiresIn() * 1000L);
                return currentAccessTokenResponse;
            }
        } catch (Exception e) {
            System.err.println("Error fetching access token: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public String getAccessToken(String tokenUrl) {
        if (isTokenExpired()) {
            AccessTokenResponse response = fetchAccessToken(tokenUrl);
            if (response != null) {
                return response.getAccessToken();
            }
        } else {
            return currentAccessTokenResponse.getAccessToken();
        }
        return null;
    }
}
