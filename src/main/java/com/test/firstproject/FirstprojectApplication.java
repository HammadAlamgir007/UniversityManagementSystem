package com.test.firstproject;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableBatchProcessing
@EnableAsync
public class FirstprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run
				(FirstprojectApplication.class,
						args);
	}

}
