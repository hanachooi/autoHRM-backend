package com.example.autoHRM_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AutoHrmBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoHrmBackendApplication.class, args);
	}

}
