package com.catalogo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.catalogo.model.Movie;
import com.catalogo.repository.MovieRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CatalogoApplicationTests {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MockMvc mockMvc;

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
	void rutaPrincipalDevuelveElCatalogoComoJson() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].title").isNotEmpty());
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
