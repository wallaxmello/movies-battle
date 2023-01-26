package com.api.moviesbattle.integration.controller;

import com.api.moviesbattle.dtos.QuizDTORequest;
import com.api.moviesbattle.dtos.UserDTO;
import com.api.moviesbattle.dtos.UserDTOStart;
import com.api.moviesbattle.dtos.UserDTOStop;
import com.api.moviesbattle.mappers.UserMapper;
import com.api.moviesbattle.models.User;
import com.api.moviesbattle.repositories.MovieRepository;
import com.api.moviesbattle.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EnableAutoConfiguration
public class MoviesBattleControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private TestRestTemplate template;

    private final HttpClient client = HttpClient.newHttpClient();


    String token;
    String movieId;

    @BeforeAll
    public void setup() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user5");
        user.setPassword(passwordEncoder.encode("$2a$12$ab0s.10sYo9ll4UlwiWyUuo43uuhPLI7UpywyRZYCRY4rvadQFMpC"));
        user.setQuizzesTotal(5);
        user.setQuizzesHits(4);
        user.setQuizzesWrong(1);
        userRepository.save(user).getId();
        user.setId(2L);
        user.setUsername("user10");
        user.setPassword(passwordEncoder.encode("$2a$12$ab0s.10sYo9ll4UlwiWyUuo43uuhPLI7UpywyRZYCRY4rvadQFMpC"));
        user.setQuizzesTotal(3);
        user.setQuizzesHits(2);
        user.setQuizzesWrong(1);
        userRepository.save(user).getId();
    }



    @Test
    @Order(1)
    void postUsers() throws URISyntaxException, IOException, InterruptedException {

        UserDTO user = new UserDTO();
        user.setUsername("user22");
        user.setPassword("$2a$12$ab0s.10sYo9ll4UlwiWyUuo43uuhPLI7UpywyRZYCRY4rvadQFMpC");
        user.setQuizzesTotal(3);
        user.setQuizzesHits(1);
        user.setQuizzesWrong(2);

        URI urlRequest = new URI("http://localhost:"+port+"/movies-battles/users");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(urlRequest);
        HttpRequest httpRequest = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(user)))
                .header("Content-Type", "application/json")
                .build();
        var retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(retorno.statusCode(), HttpStatus.OK.value());

        user.setUsername("user22");
        user.setPassword("$2a$12$ab0s.10sYo9ll4UlwiWyUuo43uuhPLI7UpywyRZYCRY4rvadQFMpC");
        httpRequest = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(user)))
                .header("Content-Type", "application/json")
                .build();
        retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(retorno.statusCode(), HttpStatus.BAD_REQUEST.value());

        user.setUsername(null);
        user.setPassword(null);
        httpRequest = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(user)))
                .header("Content-Type", "application/json")
                .build();
        retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(retorno.statusCode(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(2)
    void postStart() throws URISyntaxException, IOException, InterruptedException {

        UserDTOStart start = new UserDTOStart();
        start.setUsername("user5");
        start.setPassword("$2a$12$ab0s.10sYo9ll4UlwiWyUuo43uuhPLI7UpywyRZYCRY4rvadQFMpC");

        URI urlRequest = new URI("http://localhost:"+port+"/login");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(urlRequest);
        HttpRequest httpRequest = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(start)))
                .header("Content-Type", "application/json")
                .build();
        var retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        token = retorno.body();
        assertEquals(retorno.statusCode(), HttpStatus.OK.value());

        start.setUsername("user25");
        start.setPassword("$2a$12$ab0s.10sYo9ll4UlwiWyUuo43uuhPLI7UpywyRZYCRY4rvadQFMpC");
        httpRequest = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(start)))
                .header("Content-Type", "application/json")
                .build();
        retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(retorno.statusCode(), HttpStatus.FORBIDDEN.value());
    }

    @Test
    @Order(3)
    public void getMovies() throws Exception {

        URI urlRequest = new URI("http://localhost:"+port+"/movies-battles/movies");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(urlRequest);
        HttpRequest httpRequest = requestBuilder
                .GET()
                .header("Authorization", "Bearer " + token)
                .build();

        var retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        var start = 23;
        var end =  retorno.body().indexOf(",");
        movieId = retorno.body().substring(start, end - 1);
        assertEquals(retorno.statusCode(), HttpStatus.OK.value());

        retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(retorno.statusCode(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(4)
    void postQuizzes() throws URISyntaxException, IOException, InterruptedException {

        QuizDTORequest quizDTORequest = new QuizDTORequest();
        quizDTORequest.setUsername("user5");
        quizDTORequest.setImdbId(movieId);

        URI urlRequest = new URI("http://localhost:"+port+"/movies-battles/quizzes");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(urlRequest);
        HttpRequest httpRequest = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(quizDTORequest)))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .build();

        var retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(retorno.statusCode(), HttpStatus.OK.value());

        retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(retorno.statusCode(), HttpStatus.BAD_REQUEST.value());

        quizDTORequest.setUsername("user25");
        quizDTORequest.setImdbId(movieId);
        httpRequest = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(quizDTORequest)))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .build();
        retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(retorno.statusCode(), HttpStatus.BAD_REQUEST.value());

        quizDTORequest.setUsername("user5");
        quizDTORequest.setImdbId("tt123");
        httpRequest = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(quizDTORequest)))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .build();
        retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(retorno.statusCode(), HttpStatus.BAD_REQUEST.value());

        quizDTORequest.setUsername("user5");
        quizDTORequest.setImdbId(null);
        httpRequest = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(quizDTORequest)))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .build();
        retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(retorno.statusCode(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(5)
    public void getRatings() throws Exception {

        URI urlRequest = new URI("http://localhost:"+port+"/movies-battles/ratings");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(urlRequest);
        HttpRequest httpRequest = requestBuilder
                .GET()
                .header("Authorization", "Bearer " + token)
                .build();

        var retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(retorno.statusCode(), HttpStatus.OK.value());
    }

    @Test
    @Order(6)
    void postStop() throws URISyntaxException, IOException, InterruptedException {

        UserDTOStop userDTOStop = new UserDTOStop();
        userDTOStop.setUsername("user5");

        URI urlRequest = new URI("http://localhost:"+port+"/movies-battles/stop");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(urlRequest);
        HttpRequest httpRequest = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(userDTOStop)))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .build();

        var retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(retorno.statusCode(), HttpStatus.OK.value());

        userDTOStop.setUsername("user25");
        httpRequest = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(userDTOStop)))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .build();
        retorno = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(retorno.statusCode(), HttpStatus.BAD_REQUEST.value());
    }
}
