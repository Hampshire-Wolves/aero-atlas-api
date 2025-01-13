package com.hampshirewolves.aeroatlasapi.service;

import com.hampshirewolves.aeroatlasapi.exception.CityNotFoundException;
import com.hampshirewolves.aeroatlasapi.model.City;
import com.hampshirewolves.aeroatlasapi.model.PriceRating;
import com.hampshirewolves.aeroatlasapi.model.StarRating;
import com.hampshirewolves.aeroatlasapi.repository.CityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CityServiceImplTest {
    @Mock
    private CityRepository mockCityRepository;

    @InjectMocks
    private CityServiceImpl mockCityServiceImpl;

    @Test
    @DisplayName("getAllCities: should return all cities")
    public void testGetAllCities() {
        List<City> cityList = new ArrayList<>();
        cityList.add(City.builder()
                .name("London")
                .build());
        cityList.add(City.builder()
                .name("Berlin")
                .build());
        cityList.add(City.builder()
                .name("Madrid")
                .build());

        when(mockCityRepository.findAll()).thenReturn(cityList);

        List<City> actualResult = mockCityServiceImpl.getAllCities();
        City city1 = actualResult.getFirst();
        City city2 = actualResult.get(1);
        City city3 = actualResult.getLast();

        assertThat(actualResult).hasSize(3);
        assertThat(city1).hasFieldOrPropertyWithValue("name", "London");
        assertThat(city2).hasFieldOrPropertyWithValue("name", "Berlin");
        assertThat(city3).hasFieldOrPropertyWithValue("name", "Madrid");

        verify(mockCityRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getCityById: should return city by a given id")
    public void testGetCityById() {
        City city = City.builder()
                .id(1L)
                .name("London")
                .description("test description")
                .country("United Kingdom")
                .iataCode("LON")
                .starRating(StarRating.FOUR)
                .priceRating(PriceRating.EXPENSIVE)
                .build();

        when(mockCityRepository.findById(1L)).thenReturn(Optional.of(city));

        City actualResult = mockCityServiceImpl.getCityById(1L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(actualResult).hasFieldOrPropertyWithValue("name", "London");
        assertThat(actualResult).hasFieldOrPropertyWithValue("description", "test description");
        assertThat(actualResult).hasFieldOrPropertyWithValue("country", "United Kingdom");
        assertThat(actualResult).hasFieldOrPropertyWithValue("iataCode", "LON");
        assertThat(actualResult).hasFieldOrPropertyWithValue("starRating", StarRating.FOUR);
        assertThat(actualResult).hasFieldOrPropertyWithValue("priceRating", PriceRating.EXPENSIVE);

        verify(mockCityRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getCityById: should throw CityNotFoundException when trying to find a city that does not exist")
    public void testGetAlbumByIdThrowsWhenNotFound() {
        when(mockCityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> mockCityServiceImpl.getCityById(1L));

        verify(mockCityRepository, times(1)).findById(1L);
    }
}
