package com.intertecintl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class LoginSuggestApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(LoginSuggestApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(LoginSuggestApplication.class);

	}
}
