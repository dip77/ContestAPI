package com.constest.ContestAPI.controller;


import com.constest.ContestAPI.dto.OverAllLeaderBoardDTO;
import com.constest.ContestAPI.dto.UserPointsDTO;
import com.constest.ContestAPI.entity.OverAllLeaderBoardEntity;
import com.constest.ContestAPI.service.UserPointsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserPointsController {

    @Autowired
    UserPointsService userPointsServiceInterface;

    @RequestMapping(method = RequestMethod.POST,value = "/contest/subscribe")
    public boolean subscribe(@RequestBody UserPointsDTO userPointsDTO)
    {
        try {
            userPointsServiceInterface.save(userPointsDTO);
            OverAllLeaderBoardDTO overAllLeaderBoardDTO = new OverAllLeaderBoardDTO();
            overAllLeaderBoardDTO.setUserId(userPointsDTO.getUserId());
            overAllLeaderBoardDTO.setOverAllPoints(userPointsDTO.getFinalPoints());
            OverAllLeaderBoardEntity overAllLeaderBoardEntity = new OverAllLeaderBoardEntity();
            BeanUtils.copyProperties(overAllLeaderBoardDTO,overAllLeaderBoardEntity);
            userPointsServiceInterface.saveToHistory(overAllLeaderBoardEntity);
            return true;
        }
        catch (Exception exception)
        {
            System.out.println(exception.toString());
            return false;
        }

    }


    @RequestMapping(method = RequestMethod.GET,value = "/getAll")
    public List<UserPointsDTO> getAll()
    {
        return userPointsServiceInterface.getAllContests();
    }

    @RequestMapping(method = RequestMethod.GET,value = "/contest/getOverAllLeaderBoard")
    public List<OverAllLeaderBoardDTO> overAllLeadership()
    {
        return userPointsServiceInterface.getAll();
    }


    @RequestMapping(method = RequestMethod.GET,value = "/contest/user/{contestId}")
    public List<UserPointsDTO> getContestWiseLeaderBoard(@PathVariable("contestId") String contestId)
    {
        return userPointsServiceInterface.getByContestId(contestId);
    }
}
