package com.hampshirewolves.aeroatlasapi.service;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.resources.FlightOfferSearch;
import com.hampshirewolves.aeroatlasapi.dto.FlightOfferDTO;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FlightService {
    private final Amadeus amadeus;

    public FlightService() {
        Dotenv dotenv = Dotenv.load();
        this.amadeus = Amadeus.builder(
                dotenv.get("AMADEUS_CLIENT_KEY"),
                dotenv.get("AMADEUS_CLIENT_SECRET")
        ).build();
    }

    public List<FlightOfferDTO> getFlightOffers(Map<String, String> queryParams) throws Exception {
        Params params = convertToParams(queryParams);

        FlightOfferSearch[] flightOfferSearches = amadeus.shopping.flightOffersSearch.get(params);

        if (flightOfferSearches == null || flightOfferSearches.length == 0) {
            throw new Exception();
        }

        return mapToDTO(flightOfferSearches);
    }


    private List<FlightOfferDTO> mapToDTO(FlightOfferSearch[] flightOfferSearches) {
        return Stream.of(flightOfferSearches)
            .map(flight -> FlightOfferDTO.builder()
                    .id(flight.getId())
                    .price(flight.getPrice().getTotal())
                    .currency(flight.getPrice().getCurrency())
                    .origin(flight.getItineraries()[0].getSegments()[0].getDeparture().getIataCode())
                    .destination(flight.getItineraries()[0].getSegments()[0].getArrival().getIataCode())
                    .departureDate(flight.getItineraries()[0].getSegments()[0].getDeparture().getAt())
                    .arrivalDate(flight.getItineraries()[0].getSegments()[0].getArrival().getAt())
                    .build()
            ).collect(Collectors.toList());
    }

    public Params convertToParams(Map<String, String> queryParams) {
        if (queryParams.isEmpty()) {
            throw new IllegalArgumentException("Query parameters cannot be empty");
        }

        Map.Entry<String, String> firstEntry = queryParams.entrySet().iterator().next();
        Params params = Params.with(firstEntry.getKey(), firstEntry.getValue());

        queryParams.entrySet().stream()
                .skip(1)
                .forEach(entry -> params.and(entry.getKey(), entry.getValue()));

        return params;
    }
}
