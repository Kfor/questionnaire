package com.example.questionnaire.dao;

import com.example.questionnaire.model.QuestionnaireIp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionnaireIpDao extends JpaRepository<QuestionnaireIp, Integer> {
    List<QuestionnaireIp> findAllByQuestionnaireIdAndIp(Integer questionnaireId, String ip);
}
