package com.hampshirewolves.aeroatlasapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hampshirewolves.aeroatlasapi.dto.FlightSegmentDTO;
import com.hampshirewolves.aeroatlasapi.model.FlightRequest;
import com.hampshirewolves.aeroatlasapi.service.FlightDataService;
import com.hampshirewolves.aeroatlasapi.service.FlightDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightDataService flightDataService;
    private final FlightDetailsService flightDetailsService;

    @GetMapping
    public ResponseEntity<String> getFlights() {
        // Get and clean flight data
        String flightDataJson = flightDataService.getFlightDataJson();
        if (flightDataJson != null && !flightDataJson.isEmpty()) {
            // Convert JSON string to a list of FlightSegmentDTO
            List<FlightSegmentDTO> flightSegments = flightDetailsService.extractFlightDetails(flightDataJson);

            // If needed, convert the list back to a JSON string
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String cleanedFlightDataJson = objectMapper.writeValueAsString(flightSegments);
                return ResponseEntity.ok(cleanedFlightDataJson);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Error processing flight data.");
            }
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/fetch")
    public ResponseEntity<String> fetchFlights(@RequestBody FlightRequest request) {
        boolean success = flightDataService.fetchAndStoreFlightData(request);
        if (success) {
            return ResponseEntity.ok("Flight data successfully fetched and updated.");
        } else {
            return ResponseEntity.status(500).body("Failed to fetch flight data.");
        }
    }
}
