package com.catalogo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad persistente que representa una película de la tabla movies.
 * JPA usa esta clase para crear y consultar las columnas del catálogo.
 */
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // String se mapea a VARCHAR; obligatorio para cada película.
    @Column(nullable = false)
    private String title;

    // TEXT permite guardar una sinopsis más larga que un VARCHAR corto.
    @Column(nullable = false, columnDefinition = "TEXT")
    private String synopsis;

    // El nombre de la columna se conserva como "year", tal como pide la actividad.
    @Column(name = "year", nullable = false)
    private Integer year;

    // Se guarda la ruta o URL de la portada como texto, no la imagen binaria.
    @Column(nullable = false)
    private String cover;

    protected Movie() {
        // Constructor requerido por JPA para reconstruir entidades desde la base de datos.
    }

    public Movie(String title, String synopsis, Integer year, String cover) {
        this.title = title;
        this.synopsis = synopsis;
        this.year = year;
        this.cover = cover;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Integer getYear() {
        return year;
    }

    public String getCover() {
        return cover;
    }
}