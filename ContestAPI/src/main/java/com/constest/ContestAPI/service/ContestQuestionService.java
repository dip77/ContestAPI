package com.constest.ContestAPI.service;

import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.ContestQuestionEntity;

import java.util.List;

public interface ContestQuestionService {

    Boolean saveQuestion(ContestQuestionEntity contestQuestionEntity);
    Boolean saveQuestions(List<ContestQuestionEntity> contestQuestionEntityList);
    ContestQuestionEntity getContestQuestionById(String contestQuestionId);
    Boolean isContestExists(ContestEntity contestEntity);
}
