package com.hampshirewolves.aeroatlasapi.dto;

import com.hampshirewolves.aeroatlasapi.model.PriceRating;
import com.hampshirewolves.aeroatlasapi.model.StarRating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String country;
    private List<AttractionDTO> attractions;
    private Double lat;
    private Double lng;
    private String iataCode;
    private StarRating starRating;
    private PriceRating priceRating;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
