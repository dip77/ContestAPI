package com.constest.ContestAPI.service;

import com.constest.ContestAPI.dto.UserAnswerDTO;
import com.constest.ContestAPI.entity.UserAnswerEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserAnswerService {
    public Boolean save(UserAnswerDTO userAnswerDTO);

    public Optional<UserAnswerEntity> getSingleUser(String userAnswerId);

    public String getAnswer(String userAnswerId);

    public Boolean setAnswer(String userAnswerId, String answer);

    public List<UserAnswerEntity> getUserAllAnswers(String userId);

    public Timestamp getAnswerTime(String userAnswerId);

    public Boolean setAnswerTime(String userAnswerId, Timestamp timestamp);

    //  public Boolean getIfSkipped(String contestQuestionId, String userId);

    public String getFastestAnswer(String contestQuestionId);

    // public int getUserContestPoints(String contestId, String userId);

    public List<UserAnswerEntity> getUserEntity(String userId,String contestQuestionId);

    public int getUserScore(String userId);


}
