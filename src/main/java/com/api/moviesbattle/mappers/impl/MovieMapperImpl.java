package com.api.moviesbattle.mappers.impl;

import com.api.moviesbattle.dtos.MovieDTO;
import com.api.moviesbattle.mappers.MovieMapper;
import com.api.moviesbattle.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class MovieMapperImpl implements MovieMapper {

    @Override
    public List<MovieDTO> convertMovieListToMovieDtoList(List<Movie> movieList) {
        List<MovieDTO> movieDtoList = new ArrayList<>();
        for(var entity : movieList) {
            movieDtoList.add(new MovieDTO(
                    entity.getImdbId(),
                    entity.getTitle(),
                    entity.getYear(),
                    entity.getType(),
                    entity.getPoster()
            ));
        }
        return movieDtoList;
    }

    @Override
    public List<Movie> convertMovieDtoListToMovieList(List<MovieDTO> movieDtoList) {
        List<Movie> movieList = new ArrayList<>();
        for(var dto : movieDtoList) {
            movieList.add(new Movie(
                    dto.getImdbId(),
                    dto.getTitle(),
                    dto.getYear(),
                    dto.getType(),
                    dto.getPoster(),
                    false
            ));
        }
        return movieList;
    }

    @Override
    public List<MovieDTO> convertMovieArrayListToMovieDtoList(List<MovieDTO[]> movieArrayList) {
        List<MovieDTO> movieDtoList = new ArrayList<>();
        for(var l :movieArrayList){
            for(int i = 0; i < l.length; i++){
                movieDtoList.add(l[i]);
            }
        }
        return movieDtoList;
    }

    @Override
    public Page<MovieDTO> convertListToPage(Pageable pageable, List<MovieDTO> movieDtoList) {
        List<MovieDTO> movieDtoListReturn = movieDtoList;
        if (pageable.isPaged()) {
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;
            if (movieDtoList.size() < startItem) {
                movieDtoListReturn = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, movieDtoList.size());
                movieDtoListReturn = movieDtoList.subList(startItem, toIndex);
            }
        }
        return new PageImpl<>(movieDtoListReturn, pageable, movieDtoList.size());
    }
}
