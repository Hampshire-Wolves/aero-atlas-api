package com.hampshirewolves.aeroatlasapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightOfferDTO {
    private String id;
    private String price;
    private String currency;
    private String origin;
    private String destination;
    private String departureDate;
    private String arrivalDate;
}
