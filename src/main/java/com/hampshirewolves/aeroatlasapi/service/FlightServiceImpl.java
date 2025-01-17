package com.hampshirewolves.aeroatlasapi.service;

import com.hampshirewolves.aeroatlasapi.dto.Flight;
import com.hampshirewolves.aeroatlasapi.dto.FlightSegmentDTO;
import com.hampshirewolves.aeroatlasapi.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public FlightSegmentDTO saveFlightSegment(FlightSegmentDTO flightSegment) {
        Flight flight = new Flight();
        flight.setTripType(flightSegment.getTripType());
        flight.setDepartureAirport(flightSegment.getDepartureAirport());
        flight.setArrivalAirport(flightSegment.getArrivalAirport());
        flight.setDepartureTime(flightSegment.getDepartureTime());
        flight.setArrivalTime(flightSegment.getArrivalTime());
        flight.setCarrierCode(flightSegment.getCarrierCode());
        flight.setFlightNumber(flightSegment.getFlightNumber());
        flight.setTotalPrice(flightSegment.getTotalPrice());
        flight.setBasePrice(flightSegment.getBasePrice());

        Flight savedFlight = flightRepository.save(flight);

        return new FlightSegmentDTO(
                savedFlight.getTripType(),
                savedFlight.getDepartureAirport(),
                savedFlight.getArrivalAirport(),
                savedFlight.getDepartureTime(),
                savedFlight.getArrivalTime(),
                savedFlight.getCarrierCode(),
                savedFlight.getFlightNumber(),
                savedFlight.getTotalPrice(),
                savedFlight.getBasePrice()
        );
    }
}

