package com.sachet.postBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PostBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostBookApplication.class, args);
	}

}
