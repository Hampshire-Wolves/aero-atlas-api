package com.hampshirewolves.aeroatlasapi.service;

import com.hampshirewolves.aeroatlasapi.model.City;

import java.util.List;

public interface CityService {
    List<City> getAllCities();
    City getCityById(Long id);
    City addCity(City city);
    City updateCityById(Long id, City city);
    void deleteCityById(Long id);
}
