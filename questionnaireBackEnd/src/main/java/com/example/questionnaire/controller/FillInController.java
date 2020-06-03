package com.example.questionnaire.controller;

import com.example.questionnaire.service.CreateService;
import com.example.questionnaire.service.FillInService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FillInController {
    final
    CreateService createService;
    final
    FillInService fillInService;
    final
    Gson gson;

    public FillInController(CreateService createService, Gson gson, FillInService fillInService) {
        this.createService = createService;
        this.fillInService = fillInService;
        this.gson = gson;
    }

    @GetMapping("/api/fillin/checkAlreadySubmit")
    public Boolean checkAlreadySubmit(@Param("questionnaireId") Integer questionnaireId, @Param("ip") String ip) {
        return fillInService.checkAlreadySubmit(questionnaireId, ip);
    }

    @PostMapping("/api/fillin/submitAnswer")
    public String submitAnswer(@Param("questionnaireId") Integer questionnaireId, @RequestBody String answer) {
        String answerListJson = gson.fromJson(answer, JsonObject.class).get("answerList").toString();
        String ip = gson.fromJson(answer, JsonObject.class).get("ip").getAsString();
        if (!fillInService.checkAlreadySubmit(questionnaireId, ip))
            fillInService.submitAnswer(questionnaireId, answerListJson, ip);
        return "";
    }

    @GetMapping("/api/fillin/getQuestionList")
    public String getQuestionList(@Param("questionnaireId") Integer questionnaireId) {
        return createService.getQuestionList(questionnaireId);
    }

    @GetMapping("/api/fillin/getQuestionnaireOutline")
    public String getQuestionnaireOutline(@Param("questionnaireId") Integer questionnaireId) {
        return createService.getQuestionnaireOutline(questionnaireId);
    }
}
