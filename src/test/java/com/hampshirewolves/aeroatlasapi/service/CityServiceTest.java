package com.hampshirewolves.aeroatlasapi.service;

import com.hampshirewolves.aeroatlasapi.model.City;
import com.hampshirewolves.aeroatlasapi.model.PriceRating;
import com.hampshirewolves.aeroatlasapi.model.StarRating;
import com.hampshirewolves.aeroatlasapi.repository.CityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CityServiceTest {
    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityServiceImpl;


    @Test
    @DisplayName("When given a valid ID, the correct city should be returned")
    public void testFindCityById() {

        City city = City.builder()
                .id(1L)
                .name("London")
                .description("London, city, capital of the United Kingdom. It is among the oldest of the world's great cities—its history spanning nearly two millennia—and one of the most cosmopolitan. By far Britain's largest metropolis, it is also the country's economic, transportation, and cultural center.")
                .imageUrl("")
                .country("United Kingdom")
                .lat("51.509865")
                .lng("-0.118092")
                .iataCode("LON")
                .priceRating(PriceRating.MODERATE)
                .starRating(StarRating.FOUR)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));

        // Act
        City actualResult = cityServiceImpl.getCityById(city.getId());

        // Assert
        assertThat(actualResult.getName()).isEqualTo(city.getName());
        assertThat(actualResult.getIataCode()).isEqualTo(city.getIataCode());
        assertThat(actualResult.getLat()).isEqualTo(city.getLat());
        assertThat(actualResult.getLng()).isEqualTo(city.getLng());

    }
}
