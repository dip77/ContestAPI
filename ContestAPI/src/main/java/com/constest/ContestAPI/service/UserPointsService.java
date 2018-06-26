package com.constest.ContestAPI.service;

import com.constest.ContestAPI.dto.OverAllLeaderBoardDTO;
import com.constest.ContestAPI.dto.UserPointsDTO;
import com.constest.ContestAPI.entity.OverAllLeaderBoardEntity;
import com.constest.ContestAPI.entity.UserPointsEntity;

import java.util.List;

public interface UserPointsService {

    boolean save(UserPointsDTO userPointsDTO);
    //int getByUserId(String userId);
    boolean saveToHistory(OverAllLeaderBoardEntity overAllLeaderBoardEntity);
    List<OverAllLeaderBoardDTO> getAll();
    List<UserPointsDTO> getByContestId(String contestId);
    List<UserPointsDTO> getAllContests();
    boolean updatePoints(UserPointsDTO userPointsDTO);
    //OverAllLeaderBoardDTO getByUserId(String userId);
}
