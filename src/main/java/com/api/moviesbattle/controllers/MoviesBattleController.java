package com.api.moviesbattle.controllers;

import com.api.moviesbattle.dtos.QuizDTORequest;
import com.api.moviesbattle.dtos.UserDTO;
import com.api.moviesbattle.dtos.UserDTOStop;
import com.api.moviesbattle.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/movies-battles")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MoviesBattleController {
    private final MovieService movieService;

    @Operation(summary = "Search movies", security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/movies")
    public ResponseEntity<Object> getMovies(@PageableDefault(page = 0, size = 2) Pageable pageable) {
        try{
            var pageMovieDto = movieService.searchForMoviesToMoviesBattle(pageable);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(pageMovieDto);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Players rating",security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/ratings")
    public ResponseEntity<Object> getRatings(){
        try {
            var userDtoRatingList = movieService.searchUsersRatings();
            return ResponseEntity.status(HttpStatus.OK).body(userDtoRatingList.toString());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Answer quizzes", security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("/quizzes")
    public ResponseEntity<Object> answerQuiz(@RequestBody QuizDTORequest quizDto) {
        try {

            var quizResponse = movieService.validateQuiz(quizDto);
            return ResponseEntity.status(HttpStatus.OK).body(quizResponse);

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário não existe!");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Por favor, buscar os filmes antes, para poder votar!");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @Operation(summary = "Create player")
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
        try {
            var user = movieService.save(userDTO);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete player", security = {@SecurityRequirement(name = "bearer-key")})
    @PostMapping("/stop")
    public ResponseEntity<Object> stop(@RequestBody UserDTOStop userDtoStop) {
        try {
            var response = movieService.stopMoviesBattle(userDtoStop);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
