package com.hampshirewolves.aeroatlasapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightSegmentDTO {

    @JsonProperty("tripType")
    private String tripType;

    @JsonProperty("departureAirport")
    private String departureAirport;

    @JsonProperty("arrivalAirport")
    private String arrivalAirport;

    @JsonProperty("departureTime")
    private String departureTime;

    @JsonProperty("arrivalTime")
    private String arrivalTime;

    @JsonProperty("carrierCode")
    private String carrierCode;

    @JsonProperty("flightNumber")
    private String flightNumber;

    @JsonProperty("totalPrice")
    private double totalPrice;

    @JsonProperty("basePrice")
    private double basePrice;
}
