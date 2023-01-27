package com.api.moviesbattle.mappers;

import com.api.moviesbattle.dtos.MovieDTO;
import com.api.moviesbattle.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieMapper {
    public List<MovieDTO> convertMovieListToMovieDtoList(List<Movie> movieList);
    List<Movie> convertMovieDtoListToMovieList(List<MovieDTO> movieDtoList);
    List<MovieDTO> convertMovieArrayListToMovieDtoList(List<MovieDTO[]> movieArrayList);
    Page<MovieDTO> convertListToPage(Pageable pageable, List<MovieDTO> movieDtoList);
}
