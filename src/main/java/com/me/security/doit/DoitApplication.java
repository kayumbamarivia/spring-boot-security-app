package com.me.security.doit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class DoitApplication {

	private static final Logger logger = LoggerFactory.getLogger(DoitApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DoitApplication.class, args);
		logger.info("App is running on http://localhost:8080");
	}

}
