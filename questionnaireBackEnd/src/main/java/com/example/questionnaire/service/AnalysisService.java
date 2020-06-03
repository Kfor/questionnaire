package com.example.questionnaire.service;

import com.example.questionnaire.model.Questionnaire;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AnalysisService {
    String getQuestionnairesByUsername(String username);

    String getQuestionnaireById(Integer questionnaireId);

    String getWriteValue(Integer questionId);

    String getQuestionValueList(Integer questionId);
}
