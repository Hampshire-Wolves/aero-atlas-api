package com.hampshirewolves.aeroatlasapi.repository;

import com.hampshirewolves.aeroatlasapi.dto.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    // Additional custom query methods if needed
}

