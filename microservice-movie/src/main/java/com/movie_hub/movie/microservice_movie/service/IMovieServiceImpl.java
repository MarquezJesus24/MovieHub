package com.movie_hub.movie.microservice_movie.service;

import com.movie_hub.movie.microservice_movie.dto.MovieRequestDTO;
import com.movie_hub.movie.microservice_movie.dto.MovieResponseDTO;
import com.movie_hub.movie.microservice_movie.entity.Movie;
import com.movie_hub.movie.microservice_movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class IMovieServiceImpl implements IMovieService {

    @Autowired
    private MovieRepository movieRepository;


    @Override
    public MovieResponseDTO createMovie(MovieRequestDTO request) {
        Movie movie = Movie.builder()
                .name(request.getName())
                .description(request.getDescription())
                .rating(request.getRating())
                .status(request.getStatus())
                .createdAt(LocalDateTime.now())
                .build();

        Movie savedMovie = movieRepository.save(movie);
        return  mapToResponseDTO(savedMovie);
    }

    @Override
    public List<MovieResponseDTO> findAllMovies() {
        return StreamSupport.stream(movieRepository.findAll().spliterator(), false)
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieResponseDTO> findAllMoviesByStatus(String status) {
        return StreamSupport.stream(movieRepository.findAll().spliterator(), false)
                .filter(m -> m.getStatus().equalsIgnoreCase(status))
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovieResponseDTO findMovieById(Long id) {
        return movieRepository.findById(id)
                .map(this::mapToResponseDTO)
                .orElseThrow(()-> new RuntimeException("Película no encontrada con el ID "+ id));
    }

    @Override
    public MovieResponseDTO updateMovie(Long id, MovieRequestDTO request) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Película no encontrada con el ID "+ id));

        existingMovie.setName(request.getName());
        existingMovie.setDescription(request.getDescription());
        existingMovie.setRating(request.getRating());
        existingMovie.setStatus(request.getStatus());
        existingMovie.setUpdatedAt(LocalDateTime.now());

        Movie updatedMovie = movieRepository.save(existingMovie);
        return mapToResponseDTO(updatedMovie);

    }

    @Override
    public void deleteMovie(Long id) {
        if(!movieRepository.existsById(id)){
            throw new RuntimeException("Película no encontrada con el ID "+ id);
        }
        movieRepository.deleteById(id);
    }

    private MovieResponseDTO mapToResponseDTO(Movie movie) {
        return new MovieResponseDTO(
                movie.getId(),
                movie.getName(),
                movie.getDescription(),
                movie.getRating(),
                movie.getStatus(),
                movie.getCreatedAt()
        );
    }
}
