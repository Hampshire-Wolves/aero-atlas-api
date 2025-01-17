package com.hampshirewolves.aeroatlasapi;

import com.hampshirewolves.aeroatlasapi.dto.FlightSegmentDTO;
import com.hampshirewolves.aeroatlasapi.service.AccessTokenManager;
import com.hampshirewolves.aeroatlasapi.service.FlightDetailsService;
import com.hampshirewolves.aeroatlasapi.service.FlightFetcher;
import com.hampshirewolves.aeroatlasapi.service.FlightDataStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AeroAtlasApiApplication {

	@Autowired
	private FlightDataStore flightDataStore;

	public static void main(String[] args) {
		SpringApplication.run(AeroAtlasApiApplication.class, args);
	}

}
