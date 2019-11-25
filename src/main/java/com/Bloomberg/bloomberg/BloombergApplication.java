package com.Bloomberg.bloomberg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Spring boot application will run default webserver localhost:8080
 * Defaults can be changed in the application.properties file
 *
 * REST api will process messages on the calcualtion map POST
 *
 * Calculations need to be in the correct format
 */
@SpringBootApplication
public class BloombergApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloombergApplication.class, args);
	}
}
