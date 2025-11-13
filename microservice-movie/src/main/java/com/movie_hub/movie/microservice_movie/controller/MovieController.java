package com.movie_hub.movie.microservice_movie.controller;


import com.movie_hub.movie.microservice_movie.dto.MovieRequestDTO;
import com.movie_hub.movie.microservice_movie.dto.MovieResponseDTO;
import com.movie_hub.movie.microservice_movie.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private IMovieService iMovieService;

    // Crear Película
    @PostMapping
    public ResponseEntity<MovieResponseDTO> createMovie(@RequestBody MovieRequestDTO request) {
        MovieResponseDTO savedMovie = iMovieService.createMovie(request);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    // Obtener todas las Películas
    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> getAllMovies(){
        List<MovieResponseDTO> movies = iMovieService.findAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // Obtener todas las Películas con un estatus especifico
    @GetMapping("/all-movies-by-status/{status}")
    public ResponseEntity<List<MovieResponseDTO>> getAllMoviesByStatus(@PathVariable String status){
        List<MovieResponseDTO> movies = iMovieService.findAllMoviesByStatus(status);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // Obtener todas las Películas con un nombre especifico
    @GetMapping("/all-movies-by-name/{name}")
    public ResponseEntity<List<MovieResponseDTO>> getAllMoviesByName(@PathVariable String name){
        List<MovieResponseDTO> movies = iMovieService.findAllMoviesByName(name);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // Obtener la Película por ID
    @GetMapping("/movie/{id}")
    public ResponseEntity<MovieResponseDTO> getMovieById(@PathVariable Long id){
        try {
            MovieResponseDTO movie = iMovieService.findMovieById(id);
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar una Película
    @PutMapping("/{id}")
    public  ResponseEntity<MovieResponseDTO> updateMovie(@PathVariable Long id, @RequestBody MovieRequestDTO request){
        try {
            MovieResponseDTO movieUpdate = iMovieService.updateMovie(id, request);
            return new ResponseEntity<>(movieUpdate, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Borrar Película
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        try {
            iMovieService.deleteMovie(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
