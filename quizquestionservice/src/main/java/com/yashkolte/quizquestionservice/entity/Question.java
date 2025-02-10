package com.yashkolte.quizquestionservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qno;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false, length = 500)
    private String options;  // Options stored as a comma-separated String

    @Column(nullable = false)
    private String answer;

    public Long getQno() {
        return qno;
    }

    public void setQno(Long qno) {
        this.qno = qno;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
