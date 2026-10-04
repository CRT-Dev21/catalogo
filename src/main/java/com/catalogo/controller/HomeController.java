package com.catalogo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.catalogo.repository.MovieRepository;

@Controller
public class HomeController {

    private final MovieRepository movieRepository;

    public HomeController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Peticion principal para renderizar la vista del catalogo.
     * Equivale a la ruta web inicial del framework.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tituloPrincipal", "Catálogo de películas");
        model.addAttribute("peliculas", movieRepository.findAll());
        return "index";
    }
}
