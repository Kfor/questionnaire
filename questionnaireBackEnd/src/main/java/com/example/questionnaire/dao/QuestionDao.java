package com.example.questionnaire.dao;

import com.example.questionnaire.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findAllByQuestionnaireId(Integer questionnaireId);
    Question findDistinctByQuestionId(Integer questionId);
}
