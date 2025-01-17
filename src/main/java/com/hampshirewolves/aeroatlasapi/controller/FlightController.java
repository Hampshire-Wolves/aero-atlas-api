package com.hampshirewolves.aeroatlasapi.controller;

import com.hampshirewolves.aeroatlasapi.dto.Flight;
import com.hampshirewolves.aeroatlasapi.dto.FlightSegmentDTO;
import com.hampshirewolves.aeroatlasapi.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    @GetMapping
    public List<Flight> getFlights() {
        // Example hardcoded flights
        Flight flight1 = new Flight("One Way", "LHR", "MAD",
                "2025-01-28T09:15:00", "2025-01-28T12:40:00",
                "IB", "712", 153.21, 91.0);

        Flight flight2 = new Flight("Return", "MAD", "LHR",
                "2025-02-10T20:40:00", "2025-02-10T21:55:00",
                "IB", "3650", 153.21, 91.0);

        return Arrays.asList(flight1, flight2);
    }
}




