package com.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catalogo.model.Movie;

/** Acceso CRUD a movies; Spring Data genera la implementación automáticamente. */
public interface MovieRepository extends JpaRepository<Movie, Long> {
}