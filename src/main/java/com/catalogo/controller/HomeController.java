package com.catalogo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.catalogo.model.Movie;
import com.catalogo.repository.MovieRepository;

@RestController
public class HomeController {

    private final MovieRepository movieRepository;

    public HomeController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /** Devuelve el catálogo en JSON desde la ruta principal. */
    @GetMapping("/")
    public List<Movie> index() {
        return movieRepository.findAll();
    }
}
