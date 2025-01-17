package com.hampshirewolves.aeroatlasapi;

import com.hampshirewolves.aeroatlasapi.dto.FlightSegmentDTO;
import com.hampshirewolves.aeroatlasapi.service.AccessTokenManager;
import com.hampshirewolves.aeroatlasapi.service.FlightDetailsService;
import com.hampshirewolves.aeroatlasapi.service.FlightFetcher;
import com.hampshirewolves.aeroatlasapi.service.FlightDataStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AeroAtlasApiApplication {

	@Autowired
	private FlightDataStore flightDataStore;

	public static void main(String[] args) {
		SpringApplication.run(AeroAtlasApiApplication.class, args);
	}

	@Autowired
	public void fetchAndStoreFlightData() {
		String tokenUrl = "https://test.api.amadeus.com/v1/security/oauth2/token";
		String flightOfferUrl = "https://test.api.amadeus.com/v2/shopping/flight-offers?originLocationCode=LHR&destinationLocationCode=MAD&departureDate=2025-01-28&returnDate=2025-02-10&adults=1&max=1";

		AccessTokenManager tokenManager = new AccessTokenManager();
		FlightFetcher flightFetcher = new FlightFetcher();
		FlightDetailsService flightDetailsService = new FlightDetailsService();

		try {
			String accessToken = tokenManager.getAccessToken(tokenUrl);
			if (accessToken != null) {
				String jsonResponse = flightFetcher.fetchFlightOffers(accessToken, flightOfferUrl);
				if (jsonResponse != null) {
					List<FlightSegmentDTO> flightDetails = flightDetailsService.extractFlightDetails(jsonResponse);

					// Convert flightDetails to JSON
					ObjectMapper objectMapper = new ObjectMapper();
					String jsonOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(flightDetails);

					// Store the JSON in FlightDataStore
					flightDataStore.setFlightDataJson(jsonOutput);
				} else {
					System.out.println("Failed to fetch flight offers.");
				}
			} else {
				System.out.println("Failed to fetch access token.");
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
