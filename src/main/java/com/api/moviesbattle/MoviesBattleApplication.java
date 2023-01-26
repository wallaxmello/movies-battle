package com.api.moviesbattle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MoviesBattleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesBattleApplication.class, args);
	}
	@Bean
	public PasswordEncoder getPasswordEncoder(){
		BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();
		return encoder;
	}

}
