package com.example.questionnaire.controller;

import com.example.questionnaire.model.Questionnaire;
import com.example.questionnaire.service.AnalysisService;
import com.example.questionnaire.utils.PrettifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnalysisController {
    final
    AnalysisService analysisService;
    PrettifyUtil prettifyUtil = new PrettifyUtil();

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/api/getQuestionnaires")
    public String getQuestionnaires(Authentication authentication) {
        return analysisService.getQuestionnairesByUsername(authentication.getName());
    }

    @GetMapping("/api/getQuestionValueList")
    public String getQuestionValueList(@Param("questionId") Integer questionId) {
        return analysisService.getQuestionValueList(questionId);
    }

    @GetMapping("/api/getWriteValue")
    public String getWriteValue(@Param("questionId") Integer questionId) {
        return analysisService.getWriteValue(questionId);
    }

//

}
