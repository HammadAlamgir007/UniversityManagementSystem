package com.test.firstproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FirstprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run
				(FirstprojectApplication.class,
						args);
	}

}
