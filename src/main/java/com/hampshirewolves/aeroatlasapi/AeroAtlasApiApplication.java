package com.hampshirewolves.aeroatlasapi;

import com.hampshirewolves.aeroatlasapi.service.AccessTokenManager;
import com.hampshirewolves.aeroatlasapi.service.FlightDetailsService;
import com.hampshirewolves.aeroatlasapi.service.FlightFetcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
public class AeroAtlasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AeroAtlasApiApplication.class, args);

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
					List<org.example.dto.FlightSegmentDTO> flightDetails = flightDetailsService.extractFlightDetails(jsonResponse);

					// Convert flightDetails to JSON for printing to the console
					ObjectMapper objectMapper = new ObjectMapper();
					String jsonOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(flightDetails);

					System.out.println(jsonOutput); // Print JSON to console
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
