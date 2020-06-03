package com.example.questionnaire.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "question")
public class Question {
    @Id
    private Integer questionId;
    private Integer questionnaireId;
    private String questionTitle;
    @Column(columnDefinition = "TEXT")
    private String questionDescription;
    private Boolean questionNullable;
    private String questionType;
    @Column(columnDefinition = "TEXT")
    private String details;
}
