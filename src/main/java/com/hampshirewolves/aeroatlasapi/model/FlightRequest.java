package com.hampshirewolves.aeroatlasapi.model;

import lombok.Data;

@Data
public class FlightRequest {
    private String originLocationCode;
    private String destinationLocationCode;
    private String departureDate;
    private String returnDate;
    private int adults;
    private int max;
}
