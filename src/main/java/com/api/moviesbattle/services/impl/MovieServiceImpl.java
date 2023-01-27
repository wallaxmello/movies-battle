package com.api.moviesbattle.services.impl;

import com.api.moviesbattle.dtos.*;
import com.api.moviesbattle.mappers.MovieMapper;
import com.api.moviesbattle.mappers.UserMapper;
import com.api.moviesbattle.models.Movie;
import com.api.moviesbattle.models.User;
import com.api.moviesbattle.repositories.MovieRepository;
import com.api.moviesbattle.repositories.UserRepository;
import com.api.moviesbattle.services.MovieService;
import com.api.moviesbattle.utils.MovieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<MovieDTO> searchForMoviesToMoviesBattle(Pageable pageable){
        var movieList = movieRepository.findAll();
        var moviesDto = movieMapper.convertMovieListToMovieDtoList(movieList);
        if(!MovieUtils.quizzesHasBeenAnswered(movieList)){
            throw new IllegalArgumentException("Você não respondeu o ultimo quiz. Por favor, responda o quiz, para poder continuar! \n" +
                    "\n" + moviesDto.toString());
        }
        var moviesImdb = MovieUtils.searchForMoviesImdb();
        var movieDtoList = movieMapper.convertMovieArrayListToMovieDtoList(moviesImdb);
        movieDtoList = MovieUtils.removeDuplicateMovies(movieDtoList, movieList);
        var pageMovieDto = movieMapper.convertListToPage(pageable, movieDtoList);
        if(!pageMovieDto.getContent().isEmpty()) {
            deleteAllMovie();
            saveAllMovie(movieMapper.convertMovieDtoListToMovieList(pageMovieDto.getContent()));
        }
        return pageMovieDto;
    }

    @Override
    public List<QuizDTOResponse> searchForMoviesToMoviesBattleById() {
        List<Movie> movieList = movieRepository.findAll();

        if(movieList.isEmpty()){
            throw new EmptyResultDataAccessException(0);
        }

        if(MovieUtils.quizzesHasBeenAnswered(movieList)){
            throw new IllegalArgumentException("Você já respondeu o quiz. Busque um novo filme para continuar!");
        }

        return MovieUtils.searchForMoviesImdbById(movieList);
    }

    @Override
    public List<UserDTORating> searchUsersRatings() {
        var users = userRepository.findAll();
        if(users.isEmpty()){
            throw new UsernameNotFoundException("Não existem usuários cadastrados!");
        }
        List<UserDTORating> userDtoRatingList = users.stream()
                .map(userMapper::convertUserToUserDtoRating)
                .collect(Collectors.toList());
        return userDtoRatingList;
    }

    @Override
    public String validateQuiz(QuizDTORequest quizDtoRequest) {
        var quizMoviesId = searchForMoviesToMoviesBattleById();

        var quizIdExist = movieRepository.findByImdbId(quizDtoRequest.getImdbId());

        if (quizIdExist.isEmpty()){
            throw new IllegalArgumentException("Você já respondeu o quiz. Busque outros filmes, por favor!");
        }

        var user = userRepository.findByUsername(quizDtoRequest.getUsername());

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não cadastrado!");
        }

        var quizResponse = MovieUtils.validateQuiz(quizDtoRequest, quizMoviesId);

        user.get().setQuizzesTotal(user.get().getQuizzesTotal() + 1);
        if(quizResponse.startsWith("Você acertou")){
            user.get().setQuizzesHits(user.get().getQuizzesHits() + 1);
        } else {
            user.get().setQuizzesWrong(user.get().getQuizzesWrong() + 1);
        }
        userRepository.save(user.get());

        var movies = movieRepository.findAll();
        movies.forEach(m -> m.setAnswered(true));
        movieRepository.saveAll(movies);

        if(user.get().getQuizzesWrong() == 3) {
            userRepository.delete(user.get());
            throw new IllegalArgumentException("Usuário: " + user.get().getUsername() + ", está eliminado. Já errou 3 vezes!");
        }

        return quizResponse;
    }

    @Override
    public String stopMoviesBattle(UserDTOStop userDtoStop) {
        var user = userRepository.findByUsername(userDtoStop.getUsername());
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Usuário não existe!");
        }
        userRepository.delete(user.get());
        return "Obrigado por jogar, Movies Battle! Volte sempre!";
    }

    public void saveAllMovie(List<Movie> movieList){
        movieRepository.saveAll(movieList);
    }

    @Override
    public User save(UserDTO userDTO) {
        if(Objects.isNull(userDTO)) {
            throw new IllegalArgumentException("Objeto usuário está vazio!");
        }
        userDTO.setQuizzesTotal(0);
        userDTO.setQuizzesHits(0);
        userDTO.setQuizzesWrong(0);
        var user = userMapper.convertUserDtoToUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteAllMovie(){
        movieRepository.deleteAll();
    }
}
