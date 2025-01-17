package com.hampshirewolves.aeroatlasapi.controller;

import com.hampshirewolves.aeroatlasapi.service.FlightDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightController {

    private final FlightDetailsService flightDetailsService;

    public FlightController(FlightDetailsService flightDetailsService) {
        this.flightDetailsService = flightDetailsService;
    }

    @GetMapping("/api/flights/details")
    public ResponseEntity<List<org.example.dto.FlightSegmentDTO>> getFlightDetails(@RequestParam String jsonResponse) {
        List<org.example.dto.FlightSegmentDTO> flightDetails = flightDetailsService.extractFlightDetails(jsonResponse);
        return ResponseEntity.ok(flightDetails);
    }
}