package com.yashkolte.quizquestionservice.controller;

import com.yashkolte.quizquestionservice.entity.Question;
import com.yashkolte.quizquestionservice.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody Question question,
                                         @AuthenticationPrincipal UserDetails userDetails) {

        // Ensure only ADMIN can add questions
        if (userDetails == null || userDetails.getAuthorities().stream()
                .noneMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Only Admins can add questions.");
        }

        // Save the question
        Question savedQuestion = quizRepository.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
    }

    @PostMapping("/addall")
    public ResponseEntity<?> addAllQuestion(@RequestBody List<Question> question,
                                         @AuthenticationPrincipal UserDetails userDetails) {

        // Ensure only ADMIN can add questions
        if (userDetails == null || userDetails.getAuthorities().stream()
                .noneMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Only Admins can add questions.");
        }

        // Save the question
        List<Question> savedQuestion = quizRepository.saveAll(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
    }

    @GetMapping("/questions")
    public ResponseEntity<?> getAllQuestions() {
        List<Question> questions = quizRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(questions);
    }


}
