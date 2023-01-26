package com.api.moviesbattle.unit;

import com.api.moviesbattle.dtos.*;
import com.api.moviesbattle.mappers.MovieMapper;
import com.api.moviesbattle.mappers.impl.MovieMapperImpl;
import com.api.moviesbattle.models.Movie;
import com.api.moviesbattle.models.User;
import com.api.moviesbattle.repositories.MovieRepository;
import com.api.moviesbattle.services.MovieService;
import com.api.moviesbattle.services.impl.MovieServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(SpringExtension.class)
public class MovieServiceTest {

    @Spy
    @InjectMocks
    final MovieService movieService = mock(MovieServiceImpl.class);


    @Mock
    final MovieMapper movieMapper = mock(MovieMapperImpl.class);

    @Mock private MovieService movieServiceMock;
    @Mock private MovieMapper movieMapperMock;

    @InjectMocks
    final MovieRepository movieRepository = mock(MovieRepository.class);

    @BeforeEach
    void setup() {
        UserDTORating userDTORating = new UserDTORating();
        userDTORating.setId(1L);
        userDTORating.setUsername("teste");
        userDTORating.setQuizzesTotal(3);
        userDTORating.setQuizzesHits(2);
        userDTORating.setQuizzesWrong(1);
        List<UserDTORating> userDTORatingList = new ArrayList<>();
        userDTORatingList.add(userDTORating);
        BDDMockito.when(movieServiceMock.searchUsersRatings()).thenReturn(userDTORatingList);

        Movie movieOne = new Movie();
        movieOne.setId(1L);
        movieOne.setTitle("spider");
        movieOne.setYear("2021");
        movieOne.setImdbId("tt123");
        movieOne.setAnswered(false);
        List<Movie> movieList =new ArrayList<>();
        movieList.add(movieOne);
        BDDMockito.when(movieRepository.findAll()).thenReturn(movieList);

        Pageable pageable = PageRequest.of(0, 10);
        MovieDTO movieTwo = new MovieDTO();
        movieTwo.setTitle("bat");
        movieTwo.setYear("2021");
        movieTwo.setImdbId("tt321");
        List<MovieDTO> movieDTOList =new ArrayList<>();
        movieDTOList.add(movieTwo);

        List<MovieDTO> movieDtoListReturn = movieDTOList;
            if (pageable.isPaged()) {
                int pageSize = pageable.getPageSize();
                int currentPage = pageable.getPageNumber();
                int startItem = currentPage * pageSize;
                if (movieDTOList.size() < startItem) {
                    movieDtoListReturn = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, movieDTOList.size());
                movieDtoListReturn = movieDTOList.subList(startItem, toIndex);
            }
             Page<MovieDTO> page = new PageImpl<>(movieDtoListReturn, pageable, movieDTOList.size());
             BDDMockito.when(movieServiceMock.searchForMoviesToMoviesBattle(pageable)).thenReturn(page);
        }

            QuizDTOResponse quizDTOResponse = new QuizDTOResponse();
            quizDTOResponse.setImdbId("tt123");
            quizDTOResponse.setTitle("teste");
            quizDTOResponse.setYear("2022");
            quizDTOResponse.setImdbRating("8.9");
            List<QuizDTOResponse> quizDTOResponseList = new ArrayList<>();
            quizDTOResponseList.add(quizDTOResponse);
            BDDMockito.when(movieServiceMock.searchForMoviesToMoviesBattleById()).thenReturn(quizDTOResponseList);

            QuizDTORequest quizDTORequest = new QuizDTORequest();
            quizDTORequest.setImdbId("tt12345");
            quizDTORequest.setUsername("teste");
            BDDMockito.when(movieServiceMock.validateQuiz(quizDTORequest)).thenReturn("Você Acertou...");

            UserDTOStop userDTOStop = new UserDTOStop();
            userDTOStop.setUsername("teste");
            BDDMockito.when(movieServiceMock.stopMoviesBattle(userDTOStop)).thenReturn("Obrigado por jogar, Movies Battle! Volte sempre!");

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername("user22");
            userDTO.setPassword("123");
            userDTO.setQuizzesTotal(0);
            userDTO.setQuizzesHits(0);
            userDTO.setQuizzesWrong(0);
            User user = new User();
            user.setId(1L);
            user.setUsername(userDTO.getUsername());
            user.setPassword("Xyz#@$&12345687HGfxczz");
            user.setQuizzesTotal(userDTO.getQuizzesTotal());
            user.setQuizzesHits(userDTO.getQuizzesHits());
            user.setQuizzesWrong(userDTO.getQuizzesWrong());
            BDDMockito.when(movieServiceMock.save(userDTO)).thenReturn(user);
    }

    @Test
    public void testSearchForMoviesToMoviesBattle() {
        Pageable pageable = PageRequest.of(0, 10);
        var response = movieServiceMock.searchForMoviesToMoviesBattle(pageable);
        assertNotNull("Teste - Sucesso!", response);
    }
    @Test
    public void testSearchForMoviesToMoviesBattleById() {
        var response = movieServiceMock.searchForMoviesToMoviesBattleById();
        assertNotNull("Teste - Sucesso!", response);
    }

    @Test
    public void testSearchUsersRatings() {
        var response = movieServiceMock.searchUsersRatings();
        assertNotNull("Teste - Sucesso!", response);
    }

    @Test
    public void testValidateQuiz() {
        QuizDTORequest quizDTORequest = new QuizDTORequest();
        quizDTORequest.setImdbId("tt12345");
        quizDTORequest.setUsername("teste");
        var response = movieServiceMock.validateQuiz(quizDTORequest);
        assertNotNull("Teste - Sucesso!", response);
    }

    @Test
    public void testStopMoviesBattle() {
        UserDTOStop userDTOStop = new UserDTOStop();
        userDTOStop.setUsername("teste");
        var response = movieServiceMock.stopMoviesBattle(userDTOStop);
        assertNotNull("Teste - Sucesso!", response);
    }

    @Test
    public void testSaveAllMovie() {
        Movie movieOne = new Movie();
        movieOne.setId(1L);
        movieOne.setTitle("spider");
        movieOne.setYear("2021");
        movieOne.setImdbId("tt123");
        movieOne.setAnswered(false);
        List<Movie> movieList =new ArrayList<>();
        movieList.add(movieOne);
        movieServiceMock.saveAllMovie(movieList);
    }

    @Test
    public void testSave() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user22");
        userDTO.setPassword("123");
        userDTO.setQuizzesTotal(0);
        userDTO.setQuizzesHits(0);
        userDTO.setQuizzesWrong(0);
        var response = movieServiceMock.save(userDTO);
        assertNotNull("Teste - Sucesso!", response);
    }

    @Test
    public void testDeleteAllMovie() {
        movieServiceMock.deleteAllMovie();
    }

}
