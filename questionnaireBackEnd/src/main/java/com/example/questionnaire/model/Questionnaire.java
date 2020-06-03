package com.example.questionnaire.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "questionnaire")
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionnaireId;
    private String username;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private String status;
    private String title;
    private Integer fillCount;
    @Column(columnDefinition = "TEXT")
    private String description;
}
