package com.hampshirewolves.aeroatlasapi.service;

import com.hampshirewolves.aeroatlasapi.dto.CityDTO;

import java.util.List;

public interface CityService {
    List<CityDTO> getAllCities();
    CityDTO getCityById(Long id);
    CityDTO addCity(CityDTO city);
    CityDTO updateCityById(Long id, CityDTO city);
    void deleteCityById(Long id);
    CityDTO getRandomCity();
}
