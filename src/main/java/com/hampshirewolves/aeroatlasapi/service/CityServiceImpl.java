package com.hampshirewolves.aeroatlasapi.service;

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
}
