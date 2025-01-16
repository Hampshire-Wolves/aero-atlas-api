package com.hampshirewolves.aeroatlasapi.service;

import com.hampshirewolves.aeroatlasapi.dto.AttractionDTO;
import com.hampshirewolves.aeroatlasapi.dto.CityDTO;
import com.hampshirewolves.aeroatlasapi.exception.CityNotFoundException;
import com.hampshirewolves.aeroatlasapi.exception.MissingFieldException;
import com.hampshirewolves.aeroatlasapi.model.Attraction;
import com.hampshirewolves.aeroatlasapi.model.City;
import com.hampshirewolves.aeroatlasapi.model.PriceRating;
import com.hampshirewolves.aeroatlasapi.model.StarRating;
import com.hampshirewolves.aeroatlasapi.repository.AttractionRepository;
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

    @Mock
    private AttractionRepository mockAttractionRepository;

    @InjectMocks
    private CityServiceImpl cityServiceImpl;

    private City city;
    private Attraction attraction;
    private CityDTO cityDTO;
    private AttractionDTO attractionDTO;

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

        attraction = Attraction.builder()
                .id(1L)
                .name("Big Ben")
                .imageUrl("https://example.com/example.png")
                .city(city)
                .build();

        attractionDTO = AttractionDTO.builder()
            .id(1L)
            .name("Big Ben")
            .imageUrl("https://example.com/example.png")
            .build();

        cityDTO = CityDTO.builder()
                .id(1L)
                .name("London")
                .description("test description")
                .imageUrl("https://example.com/example.png")
                .attractions(List.of(attractionDTO))
                .country("United Kingdom")
                .lat(51.51)
                .lng(0.12)
                .iataCode("LON")
                .starRating(StarRating.FOUR)
                .priceRating(PriceRating.EXPENSIVE)
                .build();

        city.setAttractions(List.of(attraction));
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

        var actualResult = cityServiceImpl.getAllCities();
        var city1 = actualResult.getFirst();
        var city2 = actualResult.get(1);
        var city3 = actualResult.getLast();

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

        var actualResult = cityServiceImpl.getCityById(1L);

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
    public void testGetCityByIdThrowsCityNotFoundException() {
        when(mockCityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> cityServiceImpl.getCityById(1L));

        verify(mockCityRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("addCity: should create then return a given City")
    public void testAddCity() {
        when(mockCityRepository.save(any(City.class))).thenReturn(city);
        when(mockAttractionRepository.save(any(Attraction.class))).thenReturn(attraction);

        var actualResult = cityServiceImpl.addCity(cityDTO);

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
        verify(mockAttractionRepository, times(1)).save(attraction);
    }

    @Test
    @DisplayName("addCity: should throw MissingFieldException when attempting to add a City with missing/null fields")
    public void testAddCityThrowsMissingFieldException() {
        var invalidCity = City.builder()
                .name("INVALID")
                .description("INVALID")
                .build();

        var invalidCityDTO = CityDTO.builder()
                .name("INVALID")
                .description("INVALID")
                .build();

        assertThrows(MissingFieldException.class, () -> cityServiceImpl.addCity(invalidCityDTO));

        verify(mockCityRepository, never()).save(invalidCity);
    }


    @Test
    @DisplayName("updateCityById: should update and return City")
    public void testUpdateCityById() {
        when(mockCityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(mockAttractionRepository.findById(1L)).thenReturn(Optional.of(attraction));
        when(mockCityRepository.save(any(City.class))).thenReturn(city);
        when(mockAttractionRepository.save(any(Attraction.class))).thenReturn(attraction);

        var actualResult = cityServiceImpl.updateCityById(1L, cityDTO);

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
        verify(mockCityRepository, times(1)).save(city);
        verify(mockAttractionRepository, times(1)).save(attraction);
    }

    @Test
    @DisplayName("updateCityById: should throw CityNotFoundException when trying to find a City that does not exist")
    public void testUpdateCityByIdThrowsCityNotFoundException() {
        when(mockCityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> cityServiceImpl.getCityById(1L));

        verify(mockCityRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("updateCityById: should throw MissingFieldException when attempting to update a City with missing/null fields")
    public void testUpdateCityByIdThrowsMissingFieldException() {
        when(mockCityRepository.findById(1L)).thenReturn(Optional.of(city));

        var invalidCity = City.builder()
                .name("INVALID")
                .description("INVALID")
                .build();

        var invalidCityDTO = CityDTO.builder()
                .name("INVALID")
                .description("INVALID")
                .build();

        assertThrows(MissingFieldException.class, () -> cityServiceImpl.updateCityById(1L, invalidCityDTO));

        verify(mockCityRepository, times(1)).findById(1L);
        verify(mockCityRepository, never()).save(invalidCity);
    }

    @Test
    @DisplayName("deleteCityById: should delete a given City")
    public void testDeleteCityById() {
        when(mockCityRepository.findById(1L)).thenReturn(Optional.of(city));

        cityServiceImpl.deleteCityById(1L);

        verify(mockCityRepository, times(1)).findById(1L);
        verify(mockCityRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteCityById: should throw CityNotFoundException when trying to find a City that does not exist")
    public void testDeleteCityByIdThrowsCityNotFoundException() {
        when(mockCityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> cityServiceImpl.getCityById(1L));

        verify(mockCityRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("mapToDTO(City): should return city as a DTO")
    public void testMapToDTOReturnsCityDTO() {
        var result = cityServiceImpl.mapToDTO(city);

        assertThat(result).isNotNull();
        assertThat(result).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(result).hasFieldOrPropertyWithValue("name", "London");
        assertThat(result).hasFieldOrPropertyWithValue("description", "test description");
        assertThat(result).hasFieldOrPropertyWithValue("imageUrl", "https://example.com/example.png");
        assertThat(result).hasFieldOrPropertyWithValue("country", "United Kingdom");
        assertThat(result).hasFieldOrPropertyWithValue("lat", 51.51);
        assertThat(result).hasFieldOrPropertyWithValue("lng", 0.12);
        assertThat(result).hasFieldOrPropertyWithValue("iataCode", "LON");
        assertThat(result).hasFieldOrPropertyWithValue("starRating", StarRating.FOUR);
        assertThat(result).hasFieldOrPropertyWithValue("priceRating", PriceRating.EXPENSIVE);
    }

    @Test
    @DisplayName("mapToEntity(CityDTO): should return DTO as a city")
    public void testMapToEntityReturnsCity() {
        var result = cityServiceImpl.mapToEntity(cityDTO);

        assertThat(result).isNotNull();
        assertThat(result).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(result).hasFieldOrPropertyWithValue("name", "London");
        assertThat(result).hasFieldOrPropertyWithValue("description", "test description");
        assertThat(result).hasFieldOrPropertyWithValue("imageUrl", "https://example.com/example.png");
        assertThat(result).hasFieldOrPropertyWithValue("country", "United Kingdom");
        assertThat(result).hasFieldOrPropertyWithValue("lat", 51.51);
        assertThat(result).hasFieldOrPropertyWithValue("lng", 0.12);
        assertThat(result).hasFieldOrPropertyWithValue("iataCode", "LON");
        assertThat(result).hasFieldOrPropertyWithValue("starRating", StarRating.FOUR);
        assertThat(result).hasFieldOrPropertyWithValue("priceRating", PriceRating.EXPENSIVE);
    }

    @Test
    @DisplayName("mapToDTO(Attraction): should return attraction as a DTO")
    public void testMapToDTOReturnsAttractionDTO() {
        var result = cityServiceImpl.mapToDTO(attraction);

        assertThat(result).isNotNull();
        assertThat(result).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(result).hasFieldOrPropertyWithValue("name", "Big Ben");
        assertThat(result).hasFieldOrPropertyWithValue("imageUrl", "https://example.com/example.png");
    }

    @Test
    @DisplayName("mapToEntity(AttractionDTO): should return DTO as an attraction")
    public void testMapToEntityReturnsAttraction() {
        var result = cityServiceImpl.mapToEntity(attractionDTO);

        assertThat(result).isNotNull();
        assertThat(result).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(result).hasFieldOrPropertyWithValue("name", "Big Ben");
        assertThat(result).hasFieldOrPropertyWithValue("imageUrl", "https://example.com/example.png");
    }
}
