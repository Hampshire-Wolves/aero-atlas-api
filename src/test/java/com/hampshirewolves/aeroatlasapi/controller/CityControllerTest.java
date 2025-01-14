package com.hampshirewolves.aeroatlasapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hampshirewolves.aeroatlasapi.model.City;
import com.hampshirewolves.aeroatlasapi.model.PriceRating;
import com.hampshirewolves.aeroatlasapi.model.StarRating;
import com.hampshirewolves.aeroatlasapi.service.CityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class CityControllerTest {
    @Mock
    private CityServiceImpl mockCityServiceImpl;

    @InjectMocks
    private CityController cityController;

    @Autowired
    private MockMvc mockMvcController;

    private City city;

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(cityController).build();

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
    @DisplayName("GET /cities - returns all cities")
    public void testGetAllCities() throws Exception {
        List<City> cityList = new ArrayList<>();
        cityList.add(City.builder()
                .id(1L)
                .name("London")
                .build());
        cityList.add(City.builder()
                .id(2L)
                .name("Berlin")
                .build());
        cityList.add(City.builder()
                .id(3L)
                .name("Madrid")
                .build());

        when(mockCityServiceImpl.getAllCities()).thenReturn(cityList);

        this.mockMvcController.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("London"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Berlin"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Madrid"));
    }
}
