package com.example.questionnaire.controller;

import com.example.questionnaire.service.CreateService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateController {
    final
    CreateService createService;
    final Gson gson;

    public CreateController(CreateService createService, Gson gson) {
        this.createService = createService;
        this.gson = gson;
    }

    @GetMapping("/api/createQuestionnaire")
    public String createQuestionnaire(Authentication authentication) {
        return createService.createQuestionnaire(authentication.getName());
    }

    @PostMapping("/api/saveQuestionnaire")
    public String saveQuestionnaire(@RequestBody String body) {
        String questionList = gson.fromJson(body, JsonObject.class).get("questionList").toString();
        String questionnaire = gson.fromJson(body, JsonObject.class).get("questionnaire").toString();
        return createService.saveQuestionnaire(questionnaire, questionList);
    }

    @PostMapping("/api/saveQuestionnaireOutline")
    public String saveQuestionnaireOutline(@RequestBody String questionnaire) {
        return createService.saveQuestionnaireOutline(gson.fromJson(questionnaire, JsonObject.class).get("questionnaire").toString());
    }

    @PostMapping("/api/saveOneQuestion")
    public String saveOneQuestion(@RequestBody String oneQuestion, @Param("questionnaireId") Integer questionnaireId) {
        return createService.saveOneQuestion(gson.fromJson(oneQuestion, JsonObject.class).get("question").toString(), questionnaireId);
    }

    @GetMapping("/api/deleteQuestionnaire")
    public String deleteQuestionnaire(@Param("questionnaireId") Integer questionnaireId) {
        return createService.deleteQuestionnaire(questionnaireId);
    }

    /**
     * questionList: [
     * {
     * "questionId": 4000,
     * "questionnaireId": 4,
     * "questionTitle": "请输入标题第一题",
     * "questionDescription": "请输入描述",
     * "questionNullable": false,
     * "questionType": "single_check",
     * "details": "{\"questionOptions\":[\"色粉\",\"吧\",\"c\"],\"frontOptions\":[[]],\"frontChoose\":false,\"numberType\":\"integer\",\"defaultNumber\":0,\"gradeMax\":5,\"date\":\"2020-06-02T07:36:22.677Z\",\"textDescription\":\"\"}",
     * "isBoxSelected": false,
     * "questionIndex": 0,
     * "questionOptions": [
     * "色粉",
     * "吧",
     * "c"
     * ],
     * "frontOptions": [
     * []
     * ],
     * "frontChoose": false,
     * "numberType": "integer",
     * "defaultNumber": 0,
     * "gradeMax": 5,
     * "date": "2020-06-02T07:36:22.677Z",
     * "textDescription": ""
     * },
     * ]
     */
    @GetMapping("/api/getQuestionList")
    public String getQuestionList(@Param("questionnaireId") Integer questionnaireId) {
        return createService.getQuestionList(questionnaireId);
    }

    @GetMapping("/api/getQuestionnaireOutline")
    public String getQuestionnaireOutline(@Param("questionnaireId") Integer questionnaireId) {
        return createService.getQuestionnaireOutline(questionnaireId);
    }

    @PostMapping("/api/releaseQuestionnaire")
    public String releaseQuestionnaire(@Param("questionnaireId") Integer questionnaireId) {
        return createService.releaseQuestionnaire(questionnaireId);
    }

    @PostMapping("/api/closeQuestionnaire")
    public String closeQuestionnaire(@Param("questionnaireId") Integer questionnaireId) {
        return createService.closeQuestionnaire(questionnaireId);
    }
}
