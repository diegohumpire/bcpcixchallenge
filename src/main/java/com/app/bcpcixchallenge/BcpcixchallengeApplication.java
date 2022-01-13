package com.app.bcpcixchallenge;

import com.app.bcpcixchallenge.security.User;
import com.app.bcpcixchallenge.security.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// TODO: Add base prefix and Version
@SpringBootApplication
public class BcpcixchallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BcpcixchallengeApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveUser(new User(null, "dhumpire", "123456"));
		};
	}
}
