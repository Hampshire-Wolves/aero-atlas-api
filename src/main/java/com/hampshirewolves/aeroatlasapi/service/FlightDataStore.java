package com.hampshirewolves.aeroatlasapi.service;

import org.springframework.stereotype.Component;

@Component
public class FlightDataStore {
    private String flightDataJson;

    public String getFlightDataJson() {
        return flightDataJson;
    }

    public void setFlightDataJson(String flightDataJson) {
        this.flightDataJson = flightDataJson;
    }
}
