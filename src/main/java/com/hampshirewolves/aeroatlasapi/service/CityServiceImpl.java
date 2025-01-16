package com.hampshirewolves.aeroatlasapi.service;

import com.hampshirewolves.aeroatlasapi.dto.AttractionDTO;
import com.hampshirewolves.aeroatlasapi.dto.CityDTO;
import com.hampshirewolves.aeroatlasapi.exception.AttractionNotFoundException;
import com.hampshirewolves.aeroatlasapi.exception.CityNotFoundException;
import com.hampshirewolves.aeroatlasapi.exception.MissingFieldException;
import com.hampshirewolves.aeroatlasapi.model.Attraction;
import com.hampshirewolves.aeroatlasapi.model.City;
import com.hampshirewolves.aeroatlasapi.repository.AttractionRepository;
import com.hampshirewolves.aeroatlasapi.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<CityDTO> getAllCities() {
        List<City> cityList = new ArrayList<>();

        cityRepository.findAll().forEach(cityList::add);

        return cityList.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public CityDTO getCityById(Long id) {
        City foundCity = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(String.format("City with id '%s' could not be found", id)));

        return mapToDTO(foundCity);
    }

    @Override
    public CityDTO addCity(CityDTO cityDTO) {
        boolean hasValidFields = requestBodyHasValidFields(cityDTO);

        if (!hasValidFields) {
            throw new MissingFieldException("Missing field(s) in request body");
        }

        City cityEntity = mapToEntity(cityDTO);
        cityEntity.setAttractions(null);

        City savedCity = cityRepository.save(cityEntity);

        if (cityDTO.getAttractions() != null) {
            List<Attraction> attractions = cityDTO.getAttractions().stream()
                    .map(attractionDTO -> {
                        Attraction attraction = mapToEntity(attractionDTO);
                        attraction.setCity(savedCity);
                        return attractionRepository.save(attraction);
                    })
                    .collect(Collectors.toList());

            savedCity.setAttractions(attractions);
        }


        City savedCityWithAttractions = cityRepository.save(savedCity);

        return mapToDTO(savedCityWithAttractions);
    }

    @Override
    public CityDTO updateCityById(Long id, CityDTO cityDTO) {
        City foundCity = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(String.format("City with id '%s' could not be found", id)));

        boolean hasValidFields = requestBodyHasValidFields(cityDTO);

        if (!hasValidFields) {
            throw new MissingFieldException("Missing field(s) in request body");
        }

        foundCity.setName(cityDTO.getName());
        foundCity.setDescription(cityDTO.getDescription());
        foundCity.setImageUrl(cityDTO.getImageUrl());
        foundCity.setCountry(cityDTO.getCountry());
        foundCity.setLat(cityDTO.getLat());
        foundCity.setLng(cityDTO.getLng());
        foundCity.setIataCode(cityDTO.getIataCode());
        foundCity.setStarRating(cityDTO.getStarRating());
        foundCity.setPriceRating(cityDTO.getPriceRating());

        if (cityDTO.getAttractions() != null) {
            if (foundCity.getAttractions() != null) {
                foundCity.getAttractions().forEach(attraction -> attractionRepository.delete(attraction));
            }

            List<Attraction> updatedAttractions = cityDTO.getAttractions().stream()
                    .map(attractionDTO -> {
                        Attraction foundAttraction = attractionRepository.findById(attractionDTO.getId())
                                .orElseThrow(() -> new AttractionNotFoundException(String.format("Attraction with id '%s' could not be found", attractionDTO.getId())));

                        foundAttraction.setId(attractionDTO.getId());
                        foundAttraction.setName(attractionDTO.getName());
                        foundAttraction.setImageUrl(attractionDTO.getImageUrl());
                        foundAttraction.setCity(foundCity);

                        return attractionRepository.save(foundAttraction);
                    })
                    .collect(Collectors.toList());

            foundCity.setAttractions(updatedAttractions);
        }

        City updatedCity = cityRepository.save(foundCity);

        return mapToDTO(updatedCity);
    }

    @Override
    public void deleteCityById(Long id) {
        cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(String.format("City with id '%s' could not be found", id)));


        cityRepository.deleteById(id);
    }

    public CityDTO mapToDTO(City city) {
        CityDTO cityDTO = new CityDTO();

        cityDTO.setId(city.getId());
        cityDTO.setName(city.getName());
        cityDTO.setDescription(city.getDescription());
        cityDTO.setImageUrl(city.getImageUrl());
        cityDTO.setCountry(city.getCountry());
        cityDTO.setLat(city.getLat());
        cityDTO.setLng(city.getLng());
        cityDTO.setIataCode(city.getIataCode());
        cityDTO.setStarRating(city.getStarRating());
        cityDTO.setPriceRating(city.getPriceRating());
        cityDTO.setCreatedAt(city.getCreatedAt());
        cityDTO.setModifiedAt(city.getModifiedAt());

        if (city.getAttractions() != null) {
            List<AttractionDTO> attractionDTOs = city.getAttractions().stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());

            cityDTO.setAttractions(attractionDTOs);
        }

        return cityDTO;
    }

    public AttractionDTO mapToDTO(Attraction attraction){
        AttractionDTO attractionDTO = new AttractionDTO();

        attractionDTO.setId(attraction.getId());
        attractionDTO.setName(attraction.getName());
        attractionDTO.setImageUrl(attraction.getImageUrl());
        attractionDTO.setCreatedAt(attraction.getCreatedAt());
        attractionDTO.setModifiedAt(attraction.getModifiedAt());

        return attractionDTO;
    }

    public City mapToEntity(CityDTO cityDTO) {
        City city = new City();

        city.setId(cityDTO.getId());
        city.setName(cityDTO.getName());
        city.setDescription(cityDTO.getDescription());
        city.setImageUrl(cityDTO.getImageUrl());
        city.setCountry(cityDTO.getCountry());
        city.setLat(cityDTO.getLat());
        city.setLng(cityDTO.getLng());
        city.setIataCode(cityDTO.getIataCode());
        city.setStarRating(cityDTO.getStarRating());
        city.setPriceRating(cityDTO.getPriceRating());
        city.setCreatedAt(cityDTO.getCreatedAt());
        city.setModifiedAt(cityDTO.getModifiedAt());

        if (cityDTO.getAttractions() != null) {
            List<Attraction> attractions = cityDTO.getAttractions().stream()
                    .map(this::mapToEntity)
                    .collect(Collectors.toList());

            city.setAttractions(attractions);
        }

        return city;
    }

    public Attraction mapToEntity(AttractionDTO attractionDTO) {
        Attraction attraction = new Attraction();

        attraction.setId(attractionDTO.getId());
        attraction.setName(attractionDTO.getName());
        attraction.setImageUrl(attractionDTO.getImageUrl());
        attraction.setCreatedAt(attractionDTO.getCreatedAt());
        attraction.setModifiedAt(attractionDTO.getModifiedAt());

        return attraction;
    }

    private boolean requestBodyHasValidFields(CityDTO cityDTO) {
        return cityDTO.getName() != null && cityDTO.getDescription() != null
                && cityDTO.getImageUrl() != null && cityDTO.getCountry() != null
                && cityDTO.getLat() != null && cityDTO.getLng() != null
                && cityDTO.getIataCode() != null && cityDTO.getStarRating() != null
                && cityDTO.getPriceRating() != null;
    }
}
