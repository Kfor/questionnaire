package com.example.questionnaire.dao;

import com.example.questionnaire.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerDao extends JpaRepository<Answer, Integer> {
    List<Answer> findAllByQuestionId(Integer questionId);
}
