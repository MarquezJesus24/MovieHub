package com.movie_hub.movie.microservice_movie.repository;

import com.movie_hub.movie.microservice_movie.dto.MovieResponseDTO;
import com.movie_hub.movie.microservice_movie.entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

        List<MovieResponseDTO> findAllMoviesByStatus(String status);
        List<MovieResponseDTO> findAllMoviesByName(String name);

}
