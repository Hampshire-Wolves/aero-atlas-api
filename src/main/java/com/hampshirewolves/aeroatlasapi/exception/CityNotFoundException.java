package com.hampshirewolves.aeroatlasapi.exception;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(String message) {
        super(message);
    }
}
