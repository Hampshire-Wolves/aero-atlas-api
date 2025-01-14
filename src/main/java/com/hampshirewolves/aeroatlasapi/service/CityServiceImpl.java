package com.hampshirewolves.aeroatlasapi.service;

import com.hampshirewolves.aeroatlasapi.exception.CityNotFoundException;
import com.hampshirewolves.aeroatlasapi.exception.MissingFieldException;
import com.hampshirewolves.aeroatlasapi.model.City;
import com.hampshirewolves.aeroatlasapi.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        List<City> cityList = new ArrayList<>();

        cityRepository.findAll().forEach(cityList::add);

        return cityList;
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(String.format("City with id '%s' could not be found", id)));
    }

    @Override
    public City addCity(City city) {
        boolean hasValidFields = requestBodyHasValidFields(city);

        if (!hasValidFields) {
            throw new MissingFieldException("Missing field(s) in request body");
        }

        return cityRepository.save(city);
    }

    private boolean requestBodyHasValidFields(City city) {
        if (city.getName() == null || city.getDescription() == null
                || city.getImageUrl() == null || city.getCountry() == null
                || city.getLat() == null || city.getLng() == null
                || city.getIataCode() == null || city.getStarRating() == null
        || city.getPriceRating() == null) {
            return false;
        }

        return true;
    }
}
