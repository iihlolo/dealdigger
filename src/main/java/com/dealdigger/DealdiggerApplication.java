package com.dealdigger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DealdiggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DealdiggerApplication.class, args);
	}

}
