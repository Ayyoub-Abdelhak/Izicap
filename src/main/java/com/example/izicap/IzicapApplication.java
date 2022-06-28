package com.example.izicap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class IzicapApplication {

	@GetMapping
	public String message() {
		return "Hello";
	}

	public static void main(String[] args) {
		SpringApplication.run(IzicapApplication.class, args);
	}

}
