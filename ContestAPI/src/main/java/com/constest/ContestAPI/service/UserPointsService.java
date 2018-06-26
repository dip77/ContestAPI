package com.constest.ContestAPI.service;

import com.constest.ContestAPI.dto.OverAllLeaderBoardDTO;
import com.constest.ContestAPI.dto.UserPointsDTO;
import com.constest.ContestAPI.entity.OverAllLeaderBoardEntity;
import com.constest.ContestAPI.entity.UserPointsEntity;

import java.util.List;

public interface UserPointsService {

    boolean save(UserPointsDTO userPointsDTO);
    int getByUserIdAndFinalPoints(String userId,int finalPoints);
    boolean saveToHistory(OverAllLeaderBoardEntity overAllLeaderBoardEntity);
    List<OverAllLeaderBoardDTO> getAll();
    List<UserPointsDTO> getByContestId(String contestId);
    List<UserPointsDTO> getAllContests();

}
