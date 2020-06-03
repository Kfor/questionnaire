package com.example.questionnaire.service;

public interface CreateService {

    String createQuestionnaire(String username);

    String deleteQuestionnaire(Integer questionnaireId);

    String getQuestionList(Integer questionnaireId);

    String saveQuestionnaireOutline(String questionnaire);

    String saveQuestionnaire(String questionnaire, String questionList);

    String getQuestionnaireOutline(Integer questionnaireId);

    String saveOneQuestion(String question, Integer questionnaireId);

    String releaseQuestionnaire(Integer questionnaireId);

    String closeQuestionnaire(Integer questionnaireId);
}
