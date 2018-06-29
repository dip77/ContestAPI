package com.constest.ContestAPI.service;

import com.constest.ContestAPI.dto.UserPointsDTO;
import com.constest.ContestAPI.entity.LeaderBoard;
<<<<<<< HEAD
import com.constest.ContestAPI.entity.OverAllLeaderBoard;
=======
import org.springframework.stereotype.Service;
>>>>>>> c7ce109cbfc69f6aeefab478df6eb4e15e55a127

import java.util.List;

public interface UserPointsService {

    boolean save(UserPointsDTO userPointsDTO);
    List<UserPointsDTO> getAllContests();
    List<LeaderBoard> getContestWiseLeaderBoard(String contestId);
    List<LeaderBoard> getOverAllLeaderBoard();
<<<<<<< HEAD
    int getByContestId(String contestId);

=======
    //int getByContestId(String contestId);
>>>>>>> c7ce109cbfc69f6aeefab478df6eb4e15e55a127
}
