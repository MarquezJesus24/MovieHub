package com.movie_hub.movie.microservice_movie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String coverUrl;
    private Double rating;
    private String status; // "publicada" o "en edición"

    @Column(name = "created_At")
    private LocalDateTime createdAt;
    @Column(name = "updated_At")
    private LocalDateTime updatedAt;
}
