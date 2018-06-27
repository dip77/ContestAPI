package com.constest.ContestAPI.controller;
import com.constest.ContestAPI.dto.UserPointsDTO;
import com.constest.ContestAPI.entity.LeaderBoard;
import com.constest.ContestAPI.service.UserPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/userPoints/")
public class UserPointsController {

    @Autowired
    UserPointsService userPointsServiceInterface;

    @RequestMapping(method = RequestMethod.POST,value = "/contest/subscribe")
    public boolean subscribe(@RequestBody UserPointsDTO userPointsDTO)
    {
        return userPointsServiceInterface.save(userPointsDTO);
    }


    @RequestMapping(method = RequestMethod.GET,value = "/getAll")
    public List<UserPointsDTO> getAll()
    {
        return userPointsServiceInterface.getAllContests();
    }


    @RequestMapping(method = RequestMethod.GET,value = "/contest/getOverAllLeaderBoard")
    public List<LeaderBoard> overAllLeadership()
    {
        return userPointsServiceInterface.getOverAllLeaderBoard();
    }


    @RequestMapping(method = RequestMethod.GET,value = "/contest/getContestWiseLeaderBoard/{contestId}")
    public List<LeaderBoard> getContestWiseLeaderBoard(@PathVariable("contestId") String contestId)
    {
        return userPointsServiceInterface.getContestWiseLeaderBoard(contestId);
    }


}



