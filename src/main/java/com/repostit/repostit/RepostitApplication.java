package com.repostit.repostit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RepostitApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepostitApplication.class, args);
	}

}
