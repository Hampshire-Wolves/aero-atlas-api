package com.hampshirewolves.aeroatlasapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hampshirewolves.aeroatlasapi.dto.FlightSegmentDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightDetailsService {

    public List<FlightSegmentDTO> extractFlightDetails(String jsonResponse) {
        List<FlightSegmentDTO> flightSegments = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);

            JsonNode flightOffers = rootNode.path("data");
            for (JsonNode flightOffer : flightOffers) {
                double totalPrice = flightOffer.path("price").path("total").asDouble();
                double basePrice = flightOffer.path("price").path("base").asDouble();
                JsonNode itineraries = flightOffer.path("itineraries");

                for (int i = 0; i < itineraries.size(); i++) {
                    String tripType = (i == 0) ? "One Way" : "Return";
                    JsonNode segments = itineraries.get(i).path("segments");

                    for (JsonNode segment : segments) {
                        FlightSegmentDTO flightSegment = new FlightSegmentDTO(
                                tripType,
                                segment.path("departure").path("iataCode").asText(),
                                segment.path("arrival").path("iataCode").asText(),
                                segment.path("departure").path("at").asText(),
                                segment.path("arrival").path("at").asText(),
                                segment.path("carrierCode").asText(),
                                segment.path("number").asText(),
                                totalPrice,
                                basePrice
                        );
                        flightSegments.add(flightSegment);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting flight details: " + e.getMessage());
            e.printStackTrace();
        }
        return flightSegments;
    }
}
