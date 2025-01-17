package com.hampshirewolves.aeroatlasapi.service;

import com.hampshirewolves.aeroatlasapi.dto.FlightSegmentDTO;

public interface FlightService {
    FlightSegmentDTO saveFlightSegment(FlightSegmentDTO flightSegment);
}
