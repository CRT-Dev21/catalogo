package com.catalogo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.catalogo.model.Movie;
import com.catalogo.repository.MovieRepository;

/** Endpoints JSON para consultar y agregar películas al catálogo. */
@RestController
@RequestMapping("/api/movies")
public class MovieRestController {

    private final MovieRepository movieRepository;

    public MovieRestController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /** Devuelve todas las películas guardadas. */
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /** Devuelve una película o 404 si el ID no existe. */
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return movieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /** Crea una película y responde con 201 y la dirección del recurso creado. */
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody MovieRequest request) {
        if (request.title() == null || request.title().isBlank()
                || request.synopsis() == null || request.synopsis().isBlank()
                || request.year() == null
                || request.cover() == null || request.cover().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Movie savedMovie = movieRepository.save(new Movie(
                request.title(), request.synopsis(), request.year(), request.cover()));

        return ResponseEntity.created(URI.create("/api/movies/" + savedMovie.getId()))
                .body(savedMovie);
    }

    /** Estructura esperada en el JSON del POST; el id lo genera la base de datos. */
    public record MovieRequest(String title, String synopsis, Integer year, String cover) {
    }
}