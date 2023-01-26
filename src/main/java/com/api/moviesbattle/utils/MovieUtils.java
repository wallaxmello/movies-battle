package com.api.moviesbattle.utils;

import com.api.moviesbattle.dtos.MovieDTO;
import com.api.moviesbattle.dtos.QuizDTORequest;
import com.api.moviesbattle.dtos.QuizDTOResponse;
import com.api.moviesbattle.dtos.SearchDTO;
import com.api.moviesbattle.models.Movie;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MovieUtils {
    public static String[] getTitlesMovies(){
        List<String> titleMovieList = new ArrayList<>();
        titleMovieList.add("boy");
        titleMovieList.add("girl");
        titleMovieList.add("house");
        titleMovieList.add("animal");
        titleMovieList.add("space");
        titleMovieList.add("adventure");
        titleMovieList.add("lost");
        Collections.shuffle(titleMovieList);
        String[] titlesMovie = new String[2];
        titlesMovie[0] = titleMovieList.get(0);
        titlesMovie[1] = titleMovieList.get(1);
        return titlesMovie;
    }
    public static List<MovieDTO[]> searchForMoviesImdb(){
        var restTemplate = new RestTemplate();
        String[] titlesMovies = getTitlesMovies();
        List<MovieDTO[]> movieDtoArrayList = new ArrayList<>();
        for(var t : titlesMovies) {
            String urlMovie = "http://www.omdbapi.com/?apikey=32fad422&s=&type=movie&r=json&page=1&s=" + t;
            movieDtoArrayList.add(restTemplate.getForObject(urlMovie, SearchDTO.class).getSearch());
        }
        return movieDtoArrayList;
    }
    public static List<QuizDTOResponse> searchForMoviesImdbById(List<Movie> movieList){
        var restTemplate = new RestTemplate();
        List<QuizDTOResponse> quizDtoList = new ArrayList<>();
        for(var ml : movieList) {
            String urlMovie = "http://www.omdbapi.com/?apikey=32fad422&s=&type=movie&r=json&plot=short&i=" + ml.getImdbId();
            quizDtoList.add(restTemplate.getForObject(urlMovie, QuizDTOResponse.class));
        }
        return quizDtoList;
    }
    public static List<MovieDTO> removeDuplicateMovies(List<MovieDTO> movieDtoList, List<Movie> movieList){
        List<MovieDTO> movieListWithDuplicates = new ArrayList<>();
        if(!movieList.isEmpty()) {
            for(var ml : movieList){
                movieListWithDuplicates.addAll(movieDtoList.stream()
                        .filter(dto -> !dto.getImdbId().equals(ml.getImdbId()))
                        .collect(Collectors.toList()));
            }
            List<MovieDTO> movieListWithoutDuplicates = movieListWithDuplicates.stream()
                    .collect(Collectors.toSet())
                    .stream()
                    .collect(Collectors.toList());
            Collections.shuffle(movieListWithoutDuplicates);
            return movieListWithoutDuplicates;
        }
        Collections.shuffle(movieDtoList);
        return movieDtoList;
    }
    public static Boolean quizzesHasBeenAnswered(List<Movie> movieList) {
        Boolean quizzesAnswered = true;
        if(!movieList.isEmpty()) {
            for (var entity : movieList) {
                if (!entity.getAnswered()) {
                    quizzesAnswered = false;
                }
            }
        }
        return quizzesAnswered;
    }
    public static String validateQuiz(QuizDTORequest quizIdRequest, List<QuizDTOResponse> quizMoviesId){
        BigDecimal ratingAux;
        BigDecimal rating = new BigDecimal("0.0");
        String win = "";
        String lose = "";
        String quizIdWin = "";
        for(var q : quizMoviesId){
            ratingAux = new BigDecimal(q.getImdbRating());
            if(ratingAux.compareTo(rating) == 1){
                rating = ratingAux;
                lose = win;
                win = q.toString();
                quizIdWin = q.getImdbId();
            } else {
                lose = q.toString();
            }
        }
        if(quizIdWin.equals(quizIdRequest.getImdbId())){
            win = "Você acertou... parabéns!!!\n\n" + "Filme vencedor: " + win + "\n\nFilme perdedor: " + lose;
        } else {
            win = "Você errou... sinto muito!!!\n\n" + "Filme vencedor: " + win + "\n\nFilme perdedor: " + lose;
        }
        return win;
    }
}
