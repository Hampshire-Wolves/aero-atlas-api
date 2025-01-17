package com.hampshirewolves.aeroatlasapi.controller;

import com.hampshirewolves.aeroatlasapi.service.FlightDataStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightDataStore flightDataStore;

    @GetMapping
    public ResponseEntity<String> getFlights() {
        String flightDataJson = flightDataStore.getFlightDataJson();
        if (flightDataJson != null && !flightDataJson.isEmpty()) {
            return ResponseEntity.ok(flightDataJson);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
