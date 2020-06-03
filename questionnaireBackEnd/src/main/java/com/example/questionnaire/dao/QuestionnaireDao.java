package com.example.questionnaire.dao;

import com.example.questionnaire.model.Question;
import com.example.questionnaire.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionnaireDao extends JpaRepository<Questionnaire, Integer> {
    List<Questionnaire> findAllByUsername(String username);
    Questionnaire findByQuestionnaireId(Integer questionnaireId);
    @Modifying
    @Transactional
    void deleteDistinctByQuestionnaireId(Integer questionnaireId);
}
