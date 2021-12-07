package com.patnox.covidtritest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CovidTriTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidTriTestApplication.class, args);
	}

}
