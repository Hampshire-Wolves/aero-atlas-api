package com.hampshirewolves.aeroatlasapi.controller;

import com.hampshirewolves.aeroatlasapi.service.FlightDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightController {

    private final FlightDetailsService flightDetailsService;

    public FlightController(FlightDetailsService flightDetailsService) {
        this.flightDetailsService = flightDetailsService;
    }

    @GetMapping("/flights/details")
    public ResponseEntity<List<org.example.dto.FlightSegmentDTO>> getFlightDetails(@RequestParam String jsonResponse) {
        System.out.println("Received JSON Response: " + jsonResponse); // Debug output
        try {
            List<org.example.dto.FlightSegmentDTO> flightDetails = flightDetailsService.extractFlightDetails(jsonResponse);
            return ResponseEntity.ok(flightDetails);
        } catch (Exception e) {
            System.err.println("Error extracting flight details: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

}
