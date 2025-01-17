package com.hampshirewolves.aeroatlasapi.service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class FlightFetcher {

    public String fetchFlightOffers(String accessToken, String flightOfferUrl) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    flightOfferUrl,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                System.out.println("Failed to fetch data. Status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error fetching flight offers: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
