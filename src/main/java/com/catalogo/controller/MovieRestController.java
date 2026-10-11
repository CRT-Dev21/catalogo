package com.catalogo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        if (!isValid(request)) {
            return ResponseEntity.badRequest().build();
        }

        Movie savedMovie = movieRepository.save(new Movie(
                request.title(), request.synopsis(), request.year(), request.cover()));

        return ResponseEntity.created(URI.create("/api/movies/" + savedMovie.getId()))
                .body(savedMovie);
    }

    /** Reemplaza los cuatro campos de una película existente; el ID no se modifica. */
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody MovieRequest request) {
        if (!isValid(request)) {
            return ResponseEntity.badRequest().build();
        }

        return movieRepository.findById(id)
                .map(movie -> {
                    movie.updateDetails(request.title(), request.synopsis(), request.year(), request.cover());
                    return ResponseEntity.ok(movieRepository.save(movie));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /** Elimina una película existente y responde 404 si el ID no está registrado. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private boolean isValid(MovieRequest request) {
        return request != null
                && request.title() != null && !request.title().isBlank()
                && request.synopsis() != null && !request.synopsis().isBlank()
                && request.year() != null
                && request.cover() != null && !request.cover().isBlank();
    }

    /** JSON esperado en POST y PUT; la base genera el ID y PUT conserva el existente. */
    public record MovieRequest(String title, String synopsis, Integer year, String cover) {
    }
}