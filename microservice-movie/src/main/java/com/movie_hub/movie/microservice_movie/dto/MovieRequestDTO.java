package com.movie_hub.movie.microservice_movie.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequestDTO {
    private String name;
    private String description;
    private Double rating;
    private String status;
}
