package com.catalogo.config;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.catalogo.model.Movie;
import com.catalogo.repository.MovieRepository;

/**
 * Equivalente sencillo al Seeder de Laravel: inserta datos de demostración al terminar de iniciar Spring Boot.
 */
@Component
public class MovieSeeder implements ApplicationRunner {

    private final MovieRepository movieRepository;

    public MovieSeeder(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Evita duplicar las diez filas si ya existen registros en una base persistente.
        if (movieRepository.count() > 0) {
            return;
        }

        // saveAll inserta el conjunto completo usando el repositorio JPA.
        movieRepository.saveAll(List.of(
                new Movie("La llegada", "Una lingüista intenta comunicarse con visitantes extraterrestres y comprender su lenguaje.", 2016, "https://example.com/covers/la-llegada.jpg"),
                new Movie("El viaje de Chihiro", "Chihiro entra en un mundo mágico y busca la forma de liberar a sus padres.", 2001, "https://example.com/covers/el-viaje-de-chihiro.jpg"),
                new Movie("Parásitos", "Dos familias de realidades opuestas quedan unidas por un plan inesperado.", 2019, "https://example.com/covers/parasitos.jpg"),
                new Movie("Coco", "Un joven músico emprende un viaje por la Tierra de los Muertos para conocer sus raíces.", 2017, "https://example.com/covers/coco.jpg"),
                new Movie("Interestelar", "Un equipo de exploradores busca un nuevo hogar para la humanidad más allá de la Tierra.", 2014, "https://example.com/covers/interestelar.jpg"),
                new Movie("Amélie", "Una joven parisina decide mejorar discretamente la vida de quienes la rodean.", 2001, "https://example.com/covers/amelie.jpg"),
                new Movie("Spider-Man: Un nuevo universo", "Miles Morales descubre sus poderes y conoce a otros Spider-Man de distintos mundos.", 2018, "https://example.com/covers/spider-man-un-nuevo-universo.jpg"),
                new Movie("El laberinto del fauno", "En la posguerra española, una niña descubre un laberinto lleno de pruebas fantásticas.", 2006, "https://example.com/covers/el-laberinto-del-fauno.jpg"),
                new Movie("Volver al futuro", "Un adolescente viaja accidentalmente al pasado en un automóvil convertido en máquina del tiempo.", 1985, "https://example.com/covers/volver-al-futuro.jpg"),
                new Movie("Ratatouille", "Una rata apasionada por la cocina encuentra la oportunidad de demostrar su talento en París.", 2007, "https://example.com/covers/ratatouille.jpg")
        ));
    }
}