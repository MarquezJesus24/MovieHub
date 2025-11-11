package com.movie_hub.movie.microservice_movie.service;

import com.movie_hub.movie.microservice_movie.dto.MovieRequestDTO;
import com.movie_hub.movie.microservice_movie.dto.MovieResponseDTO;

import java.util.List;
import java.util.Optional;

public interface IMovieService {

    // C - CREATE
    MovieResponseDTO createMovie(MovieRequestDTO request);

    // R - READ
    List<MovieResponseDTO> findAllMovies();
    List<MovieResponseDTO> findAllMoviesByStatus(String status);
    MovieResponseDTO findMovieById(Long id);

    // U - UPDATE
    MovieResponseDTO updateMovie(Long id, MovieRequestDTO request);

    // D - DELETE
    void deleteMovie(Long id);



}
