package com.catalogo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.catalogo.model.Movie;
import com.catalogo.repository.MovieRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CatalogoApplicationTests {

	@Autowired
	private MovieRepository movieRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void cargaDiezPeliculasDeEjemplo() {
		assertThat(movieRepository.count()).isEqualTo(10);
		assertThat(movieRepository.findAll())
				.allSatisfy(pelicula -> {
					assertThat(pelicula.getTitle()).isNotBlank();
					assertThat(pelicula.getSynopsis()).isNotBlank();
					assertThat(pelicula.getYear()).isBetween(1900, 2100);
					assertThat(pelicula.getCover()).isNotBlank();
				});
	}

	@Test
	@Transactional
	void actualizaPeliculaSinCambiarSuId() {
		Movie movie = movieRepository.findAll().get(0);
		Long originalId = movie.getId();

		movie.updateDetails("Título actualizado", "Sinopsis actualizada", 2025, "https://example.com/updated.jpg");
		Movie updatedMovie = movieRepository.save(movie);

		assertThat(updatedMovie.getId()).isEqualTo(originalId);
		assertThat(updatedMovie.getTitle()).isEqualTo("Título actualizado");
		assertThat(updatedMovie.getSynopsis()).isEqualTo("Sinopsis actualizada");
		assertThat(updatedMovie.getYear()).isEqualTo(2025);
		assertThat(updatedMovie.getCover()).isEqualTo("https://example.com/updated.jpg");
	}

	@Test
	@Transactional
	void eliminaPeliculaExistente() {
		Movie movie = movieRepository.findAll().get(0);
		Long movieId = movie.getId();

		movieRepository.deleteById(movieId);

		assertThat(movieRepository.existsById(movieId)).isFalse();
	}

}
