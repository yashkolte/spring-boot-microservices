package com.yashkolte.quizquestionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class QuizQuestionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizQuestionServiceApplication.class, args);
	}

}
