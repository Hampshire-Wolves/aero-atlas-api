package com.hampshirewolves.aeroatlasapi.service;

import com.hampshirewolves.aeroatlasapi.model.FlightRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightDataService {

    private final FlightDataStore flightDataStore;
    private final AccessTokenManager tokenManager;
    private final FlightFetcher flightFetcher;
    private final FlightDetailsService flightDetailsService;

    @Autowired
    public FlightDataService(FlightDataStore flightDataStore, AccessTokenManager tokenManager,
                             FlightFetcher flightFetcher, FlightDetailsService flightDetailsService) {
        this.flightDataStore = flightDataStore;
        this.tokenManager = tokenManager;
        this.flightFetcher = flightFetcher;
        this.flightDetailsService = flightDetailsService;
    }

    public boolean fetchAndStoreFlightData(FlightRequest request) {
        String tokenUrl = "https://test.api.amadeus.com/v1/security/oauth2/token";

        // Constructing the flightOfferUrl dynamically based on the request
        String flightOfferUrl = String.format(
                "https://test.api.amadeus.com/v2/shopping/flight-offers?originLocationCode=%s&destinationLocationCode=%s&departureDate=%s&returnDate=%s&adults=%d&max=%d",
                request.getOriginLocationCode(),
                request.getDestinationLocationCode(),
                request.getDepartureDate(),
                request.getReturnDate(),
                request.getAdults(),
                request.getMax()
        );

        try {
            String accessToken = tokenManager.getAccessToken(tokenUrl);
            if (accessToken != null) {
                String jsonResponse = flightFetcher.fetchFlightOffers(accessToken, flightOfferUrl);
                if (jsonResponse != null) {
                    flightDataStore.setFlightDataJson(jsonResponse);
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }

    public String getFlightDataJson() {
        return flightDataStore.getFlightDataJson();
    }
}
