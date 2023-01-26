package com.api.moviesbattle.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_filmes")
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;

    public Movie(String imdbId, String title, String year, String type, String poster, Boolean answered) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.type = type;
        this.poster = poster;
        this.answered = answered;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "imdb_id", unique = true, nullable = false)
    private String imdbId;

    @Column(name = "titulo")
    private String title;

    @Column(name = "ano")
    private String year;

    @Column(name = "tipo")
    private String type;

    @Column(name = "capa")
    private String poster;

    @Column(name = "respondido")
    private Boolean answered;
}
