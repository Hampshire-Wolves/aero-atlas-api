package com.hampshirewolves.aeroatlasapi.service;

import com.hampshirewolves.aeroatlasapi.exception.CityNotFoundException;
import com.hampshirewolves.aeroatlasapi.exception.MissingFieldException;
import com.hampshirewolves.aeroatlasapi.model.City;
import com.hampshirewolves.aeroatlasapi.model.PriceRating;
import com.hampshirewolves.aeroatlasapi.model.StarRating;
import com.hampshirewolves.aeroatlasapi.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
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

    private City city;

    @BeforeEach
    public void setUp() {
        city = City.builder()
                .id(1L)
                .name("London")
                .description("test description")
                .imageUrl("https://example.com/example.png")
                .country("United Kingdom")
                .lat(51.51)
                .lng(0.12)
                .iataCode("LON")
                .starRating(StarRating.FOUR)
                .priceRating(PriceRating.EXPENSIVE)
                .build();
    }

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
    @DisplayName("getCityById: should return a City by a given id")
    public void testGetCityById() {
        when(mockCityRepository.findById(1L)).thenReturn(Optional.of(city));

        City actualResult = mockCityServiceImpl.getCityById(1L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(actualResult).hasFieldOrPropertyWithValue("name", "London");
        assertThat(actualResult).hasFieldOrPropertyWithValue("description", "test description");
        assertThat(actualResult).hasFieldOrPropertyWithValue("imageUrl", "https://example.com/example.png");
        assertThat(actualResult).hasFieldOrPropertyWithValue("country", "United Kingdom");
        assertThat(actualResult).hasFieldOrPropertyWithValue("lat", 51.51);
        assertThat(actualResult).hasFieldOrPropertyWithValue("lng", 0.12);
        assertThat(actualResult).hasFieldOrPropertyWithValue("iataCode", "LON");
        assertThat(actualResult).hasFieldOrPropertyWithValue("starRating", StarRating.FOUR);
        assertThat(actualResult).hasFieldOrPropertyWithValue("priceRating", PriceRating.EXPENSIVE);

        verify(mockCityRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getCityById: should throw CityNotFoundException when trying to find a City that does not exist")
    public void testGetAlbumByIdThrowsWhenNotFound() {
        when(mockCityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> mockCityServiceImpl.getCityById(1L));

        verify(mockCityRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("addCity: should create then return a given City")
    public void testAddCity() {
        when(mockCityRepository.save(city)).thenReturn(city);

        City actualResult = mockCityServiceImpl.addCity(city);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(actualResult).hasFieldOrPropertyWithValue("name", "London");
        assertThat(actualResult).hasFieldOrPropertyWithValue("description", "test description");
        assertThat(actualResult).hasFieldOrPropertyWithValue("imageUrl", "https://example.com/example.png");
        assertThat(actualResult).hasFieldOrPropertyWithValue("country", "United Kingdom");
        assertThat(actualResult).hasFieldOrPropertyWithValue("lat", 51.51);
        assertThat(actualResult).hasFieldOrPropertyWithValue("lng", 0.12);
        assertThat(actualResult).hasFieldOrPropertyWithValue("iataCode", "LON");
        assertThat(actualResult).hasFieldOrPropertyWithValue("starRating", StarRating.FOUR);
        assertThat(actualResult).hasFieldOrPropertyWithValue("priceRating", PriceRating.EXPENSIVE);

        verify(mockCityRepository, times(1)).save(city);
    }

    @Test
    @DisplayName("addCity: should throw MissingFieldException when attempting to add a City with missing/null fields")
    public void testAddCityThrowsException() {
        City invalidCity = City.builder()
                .name("INVALID")
                .description("INVALID")
                .build();

        when(mockCityRepository.save(city)).thenReturn(city)
                .thenThrow(new MissingFieldException("Missing field(s) in request body"));

        assertThrows(MissingFieldException.class, () -> mockCityServiceImpl.addCity(invalidCity));

        verify(mockCityRepository, never()).save(invalidCity);
    }
}
