package com.movie_hub.movie.microservice_movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String status;
    private LocalDateTime createdAt;
}
