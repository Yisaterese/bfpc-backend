package com.bfpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main application class for the Benue Farmers Peace Corps (BFPC) backend system.
 * This application provides APIs for agricultural empowerment, market linkage,
 * yield optimization, and farmer support services.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class BfpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(BfpcApplication.class, args);
	}

}