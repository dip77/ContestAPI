package com.constest.ContestAPI.service;

import com.constest.ContestAPI.dto.UserAnswerDTO;
import com.constest.ContestAPI.entity.UserAnswerEntity;

import java.util.List;
import java.util.Optional;

public interface UserAnswerService {
    public Boolean save(UserAnswerDTO userAnswerDTO);

    public Optional<UserAnswerEntity> getSingleUser(String userAnswerId);

    public List<UserAnswerEntity> getUserAllAnswers(String userId);

    public String getFastestAnswer(String contestQuestionId) throws InterruptedException;

    public UserAnswerEntity getUserEntity(String userId, String contestQuestionId);

    public String checkAnswer(String questionId, String answer);


}
