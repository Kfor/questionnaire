package com.example.questionnaire.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;
    private Integer questionId;
    private String questionTitle;
    private String questionType;
    @Column(columnDefinition = "TEXT")
    private String writeValue;
    private String ip;
    private Date fillTime;
}
