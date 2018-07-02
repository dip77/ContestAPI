package com.constest.ContestAPI.service;

import com.constest.ContestAPI.entity.ContestEntity;

import java.util.List;

public interface ContestService {
     ContestEntity saveContest(ContestEntity contestEntity);
     List<ContestEntity> getAll();
     List<ContestEntity> getAllByCategory(String categoryId);
     List<ContestEntity> getAllByContestType(String contestType);
     List<ContestEntity> getContestByAdmin(String adminId);
    ContestEntity getAllContestQuestions(String contestId);
}
