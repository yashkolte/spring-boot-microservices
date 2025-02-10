package com.yashkolte.quizquestionservice.controller;

import com.yashkolte.quizquestionservice.dto.UserDto;
import com.yashkolte.quizquestionservice.entity.Question;
import com.yashkolte.quizquestionservice.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "http://localhost:3000")
public class AnswerSubmissionController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String userServiceUrl = "http://localhost:8081/user/";

    @PostMapping("/submit")
    public ResponseEntity<?> submitAnswers(@RequestParam Map<String, String> answers,
                                           @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Basic authentication required");
        }

        try {
            // Forward the exact same Authorization header
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);

            // Get user details
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            ResponseEntity<UserDto> userResponse = restTemplate.exchange(
                    userServiceUrl + "get/" + username,
                    HttpMethod.GET,
                    requestEntity,
                    UserDto.class
            );

            if (!userResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(userResponse.getStatusCode())
                        .body("Failed to retrieve user data");
            }

            UserDto user = userResponse.getBody();
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            // Calculate score
            int score = 0;
            for (Map.Entry<String, String> entry : answers.entrySet()) {
                try {
                    Long qno = Long.valueOf(entry.getKey());
                    String userAnswer = entry.getValue();

                    Question question = quizRepository.findById(qno).orElse(null);
                    if (question != null && question.getAnswer().equalsIgnoreCase(userAnswer)) {
                        score++;
                    }
                } catch (NumberFormatException ex) {
                    continue;
                }
            }

            user.setScore(score);

            // Update user score
            HttpEntity<UserDto> updateEntity = new HttpEntity<>(user, headers);
            ResponseEntity<Void> updateResponse = restTemplate.exchange(
                    userServiceUrl + "update/" + username,
                    HttpMethod.PUT,
                    updateEntity,
                    Void.class
            );

            if (!updateResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(updateResponse.getStatusCode())
                        .body("Failed to update user score");
            }

            return ResponseEntity.ok("Your score: " + score);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing quiz submission: " + e.getMessage());
        }
    }
}