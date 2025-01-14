package com.hampshirewolves.aeroatlasapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cities")
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;

    @Column(nullable = false)
    private String iataCode;

    @Column(nullable = false, name = "star_rating")
    @Enumerated(EnumType.STRING)
    private StarRating starRating;

    @Column(nullable = false, name = "price_rating")
    @Enumerated(EnumType.STRING)
    private PriceRating priceRating;

    @Column(name = "created_at", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
