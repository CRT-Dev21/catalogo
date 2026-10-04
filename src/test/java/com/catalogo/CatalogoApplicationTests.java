package com.catalogo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
