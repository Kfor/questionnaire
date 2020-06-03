package com.example.questionnaire.service.impl;

import com.example.questionnaire.dao.AnswerDao;
import com.example.questionnaire.dao.QuestionDao;
import com.example.questionnaire.dao.QuestionnaireDao;
import com.example.questionnaire.model.Answer;
import com.example.questionnaire.model.Question;
import com.example.questionnaire.service.AnalysisService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    final
    QuestionnaireDao questionnaireDao;
    final
    QuestionDao questionDao;
    final
    AnswerDao answerDao;
    Gson gson = new Gson();

    public AnalysisServiceImpl(QuestionnaireDao questionnaireDao, QuestionDao questionDao, AnswerDao answerDao) {
        this.questionnaireDao = questionnaireDao;
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @Override
    public String getQuestionnairesByUsername(String username) {
        JsonObject res = new JsonObject();
        res.add("questionnaires", gson.fromJson(gson.toJson(questionnaireDao.findAllByUsername(username)), JsonArray.class));
        return gson.toJson(res);
    }

    @Override
    public String getQuestionnaireById(Integer questionnaireId) {
        return gson.toJson(questionnaireDao.findByQuestionnaireId(questionnaireId));
    }

    @Override
    public String getWriteValue(Integer questionId) {
        return gson.toJson(answerDao.findAllByQuestionId(questionId));
    }

    @Transactional
    @Override
    public String getQuestionValueList(Integer questionId) {
        List<Answer> answerList = answerDao.findAllByQuestionId(questionId);

        Question question = questionDao.findDistinctByQuestionId(questionId);
        String questionType = question.getQuestionType();


        if (questionType.equals("single_check")) {
            Map<String, Integer> resValueMap = new HashMap<>();
            JsonObject temp = gson.fromJson(question.getDetails(), JsonObject.class);
            JsonArray questionOptionsJsonArray = temp.get("questionOptions").getAsJsonArray();
            for (JsonElement questionOptionJson : questionOptionsJsonArray
            ) {
                String questionOption = questionOptionJson.getAsString();
                resValueMap.put(questionOption, 0);
            }
            System.out.println(temp);
            System.out.println(resValueMap);
            for (Answer oneAnswer : answerList
            ) {
                String value = oneAnswer.getWriteValue();
                Integer oldCount = resValueMap.get(value);
                resValueMap.put(value, oldCount + 1);
            }
            return gson.toJson(resValueMap);
        } else if (questionType.equals("multi_check")) {
            Map<String, Integer> resValueMap = new HashMap<>();
            JsonObject temp = gson.fromJson(question.getDetails(), JsonObject.class);
            JsonArray questionOptionsJsonArray = temp.get("questionOptions").getAsJsonArray();
            for (JsonElement questionOptionJson : questionOptionsJsonArray
            ) {
                String questionOption = questionOptionJson.getAsString();
                resValueMap.put(questionOption, 0);
            }
            for (Answer oneAnswer : answerList
            ) {
                JsonArray valueList = gson.fromJson(oneAnswer.getWriteValue(), JsonArray.class);
                for (JsonElement value : valueList
                ) {
                    Integer oldCount = resValueMap.get(value.getAsString());
                    resValueMap.put(value.getAsString(), oldCount + 1);
                }
            }
            return gson.toJson(resValueMap);
        } else if (questionType.equals("number") || questionType.equals("grade")) {
            Map<String, Double> resValueMap = new HashMap<>();
            List<Double> valueList = new ArrayList<>();
            Double sum = 0.0;
            for (Answer oneAnswer : answerList
            ) {
                Double value = gson.fromJson(oneAnswer.getWriteValue(), Double.class);
                valueList.add(value);
                sum += value;
            }
            valueList.sort((a, b) -> (int) (a - b));
            System.out.println(valueList);

            if(valueList.size()==0){
                resValueMap.put("最大值", 0.0);
                resValueMap.put("最小值", 0.0);
                resValueMap.put("平均值", 0.0);
                resValueMap.put("中位数", 0.0);
            }else{
                resValueMap.put("最大值", valueList.get(valueList.size() - 1));
                resValueMap.put("最小值", valueList.get(0));
                resValueMap.put("平均值", sum / valueList.size());
                resValueMap.put("中位数", valueList.get(valueList.size() / 2));
            }

            return gson.toJson(resValueMap);
        }
        return null;
    }

}
