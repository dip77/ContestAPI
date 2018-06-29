package com.constest.ContestAPI.service;

import com.constest.ContestAPI.dto.UserPointsDTO;
import com.constest.ContestAPI.entity.LeaderBoard;

import java.util.List;

public interface UserPointsService {

    boolean save(UserPointsDTO userPointsDTO);
    List<UserPointsDTO> getAllContests();
    List<LeaderBoard> getContestWiseLeaderBoard(String contestId);
    List<LeaderBoard> getOverAllLeaderBoard();
    int getByContestId(String contestId);
}
