package com.api.moviesbattle.services;

import com.api.moviesbattle.dtos.*;
import com.api.moviesbattle.models.Movie;
import com.api.moviesbattle.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    Page<MovieDTO> searchForMoviesToMoviesBattle(Pageable pageable);
    List<QuizDTOResponse> searchForMoviesToMoviesBattleById();
    List<UserDTORating> searchUsersRatings();
    String validateQuiz(QuizDTORequest quizDTO);
    String stopMoviesBattle(UserDTOStop userDtoStop);
    void saveAllMovie(List<Movie> movieList);
    User save(UserDTO userDTO);
    void deleteAllMovie();
}
