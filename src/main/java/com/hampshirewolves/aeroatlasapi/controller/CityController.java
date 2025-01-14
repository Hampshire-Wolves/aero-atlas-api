package com.hampshirewolves.aeroatlasapi.controller;

import com.hampshirewolves.aeroatlasapi.model.City;
import com.hampshirewolves.aeroatlasapi.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        List<City> albums = cityService.getAllCities();

        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        return new ResponseEntity<>(cityService.getCityById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<City> addCity(@RequestBody City city) {
        return new ResponseEntity<>(cityService.addCity(city), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCityById(@PathVariable Long id, @RequestBody City city) {
        return new ResponseEntity<>(cityService.updateCityById(id, city), HttpStatus.OK);
    }
}
