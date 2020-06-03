package com.example.questionnaire.service.impl;

import com.example.questionnaire.dao.QuestionDao;
import com.example.questionnaire.dao.QuestionnaireDao;
import com.example.questionnaire.model.Question;
import com.example.questionnaire.model.Questionnaire;
import com.example.questionnaire.service.CreateService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CreateServiceImpl implements CreateService {
    final
    QuestionnaireDao questionnaireDao;
    final
    QuestionDao questionDao;
    final Integer QuestionIdDigit = 1000;

    Gson gson = new Gson();

    public CreateServiceImpl(QuestionnaireDao questionnaireDao, QuestionDao questionDao) {
        this.questionnaireDao = questionnaireDao;
        this.questionDao = questionDao;
    }

    @Override
    public String createQuestionnaire(String username) {
        Questionnaire newQuestionnaire = new Questionnaire();
        newQuestionnaire.setUsername(username);
        newQuestionnaire.setCreateTime(new Date());
        newQuestionnaire.setStatus("editing");
        newQuestionnaire.setTitle("请输入标题");
        newQuestionnaire.setDescription("请输入描述");
        questionnaireDao.save(newQuestionnaire);

        JsonObject res = new JsonObject();
        res.addProperty("id", newQuestionnaire.getQuestionnaireId());

        return gson.toJson(res);
    }

    @Override
    public String deleteQuestionnaire(Integer questionnaireId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getName().equals(questionnaireDao.findByQuestionnaireId(questionnaireId).getUsername())) {
            questionnaireDao.deleteDistinctByQuestionnaireId(questionnaireId);
            return "success";
        } else {
            return "denied!";
        }
    }

    /**
     * questionIndex: this.questionList.length,
     * isBoxSelected: true,
     * questionTitle: "请输入标题",
     * questionNullable: false,
     * questionDescription: "请输入描述",
     * questionType: type,
     *
     * questionOptions: [],
     * frontOptions: [[]],
     * frontChoose: false,
     * numberType: 'integer',
     * defaultNumber: 0,
     * gradeMax: 5,
     * date: new Date(),
     * textDescription: '',
     */
    @Override
    public String getQuestionList(Integer questionnaireId) {

        JsonArray resList = new JsonArray();

        List<Question> questionList = questionDao.findAllByQuestionnaireId(questionnaireId);
        for (Question oneQuestion : questionList
        ) {

            JsonObject oneRes = gson.fromJson(gson.toJson(oneQuestion), JsonObject.class);
            oneRes.addProperty("isBoxSelected", false);
            oneRes.addProperty("questionTitle", oneQuestion.getQuestionTitle());
            oneRes.addProperty("questionDescription", oneQuestion.getQuestionDescription());
            oneRes.addProperty("questionIndex", oneQuestion.getQuestionId() % QuestionIdDigit);
            oneRes.addProperty("questionNullable", oneQuestion.getQuestionNullable());
            oneRes.addProperty("questionType", oneQuestion.getQuestionType());

            JsonObject temp = gson.fromJson(oneQuestion.getDetails(), JsonObject.class);

            processDetails(oneRes, temp);

            resList.add(oneRes);
        }
        JsonObject res = new JsonObject();
        res.add("questionList", resList);
        return gson.toJson(res);
    }

    private void processDetails(JsonObject oneRes, JsonObject temp) {
        if (temp != null) {
            oneRes.add("questionOptions", temp.get("questionOptions").getAsJsonArray());
            oneRes.add("frontOptions", temp.get("frontOptions").getAsJsonArray());
            oneRes.addProperty("frontChoose", temp.get("frontChoose").getAsBoolean());
            oneRes.addProperty("numberType", temp.get("numberType").getAsString());
            oneRes.addProperty("defaultNumber", temp.get("defaultNumber").getAsInt());
            oneRes.addProperty("gradeMax", temp.get("gradeMax").getAsInt());
            oneRes.addProperty("date", temp.get("date").getAsString());
            oneRes.addProperty("textDescription", temp.get("textDescription").getAsString());
        }

    }

    @Override
    public String saveQuestionnaireOutline(String questionnaire) {
        JsonObject questionnaireObject = gson.fromJson(questionnaire, JsonObject.class);
        Questionnaire questionnaireEntity = questionnaireDao.findByQuestionnaireId(questionnaireObject.get("questionnaireId").getAsInt());
        questionnaireEntity.setTitle(questionnaireObject.get("questionnaireTitle").getAsString());
        questionnaireEntity.setDescription(questionnaireObject.get("questionnaireDescription").getAsString());
        questionnaireDao.save(questionnaireEntity);
        return null;
    }

    @Override
    public String saveQuestionnaire(String questionnaire, String questionList) {
        JsonObject questionnaireObject = gson.fromJson(questionnaire, JsonObject.class);
        Integer questionnaireId = questionnaireObject.get("questionnaireId").getAsInt();
        JsonArray questionListArray = gson.fromJson(questionList, JsonArray.class);

        saveQuestionnaireOutline(questionnaire);

        for (JsonElement oneQuestionJson : questionListArray
        ) {
            JsonObject temp = oneQuestionJson.getAsJsonObject();
            saveOneQuestion(questionnaireId, temp);
        }
        return null;
    }

    @Override
    public String getQuestionnaireOutline(Integer questionnaireId) {
        JsonObject res = new JsonObject();
        res.add("questionnaire", gson.fromJson(gson.toJson(questionnaireDao.findByQuestionnaireId(questionnaireId)), JsonObject.class));
        return gson.toJson(res);
    }

    @Override
    public String saveOneQuestion(String question, Integer questionnaireId) {
        JsonObject temp = gson.fromJson(question, JsonObject.class);;
        saveOneQuestion(questionnaireId, temp);
        return null;
    }

    @Override
    public String releaseQuestionnaire(Integer questionnaireId) {
        Questionnaire questionnaire = questionnaireDao.findByQuestionnaireId(questionnaireId);
        questionnaire.setStatus("collecting");
        questionnaire.setFillCount(0);
        questionnaireDao.save(questionnaire);
        return null;
    }

    @Override
    public String closeQuestionnaire(Integer questionnaireId) {
        Questionnaire questionnaire = questionnaireDao.findByQuestionnaireId(questionnaireId);
        questionnaire.setStatus("closed");
        questionnaire.setFillCount(0);
        questionnaireDao.save(questionnaire);
        return null;
    }

    private void saveOneQuestion(Integer questionnaireId, JsonObject temp) {
        Question oneQuestion = new Question();
        oneQuestion.setQuestionnaireId(questionnaireId);
        oneQuestion.setQuestionDescription(temp.get("questionDescription").getAsString());
        oneQuestion.setQuestionTitle(temp.get("questionTitle").getAsString());
        oneQuestion.setQuestionId(questionnaireId * QuestionIdDigit + temp.get("questionIndex").getAsInt());
        oneQuestion.setQuestionType(temp.get("questionType").getAsString());
        oneQuestion.setQuestionNullable(temp.get("questionNullable").getAsBoolean());

        JsonObject otherJson = new JsonObject();
        processDetails(otherJson, temp);

        oneQuestion.setDetails(otherJson.toString());
        questionDao.save(oneQuestion);
    }

}
