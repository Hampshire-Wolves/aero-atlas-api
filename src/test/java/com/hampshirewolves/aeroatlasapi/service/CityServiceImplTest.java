package com.hampshirewolves.aeroatlasapi.service;

import com.hampshirewolves.aeroatlasapi.model.City;
import com.hampshirewolves.aeroatlasapi.repository.CityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
}
