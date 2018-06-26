package com.constest.ContestAPI.controller;


import com.constest.ContestAPI.dto.OverAllLeaderBoardDTO;
import com.constest.ContestAPI.dto.UserPointsDTO;
import com.constest.ContestAPI.entity.OverAllLeaderBoardEntity;
import com.constest.ContestAPI.service.UserPointsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
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


    @RequestMapping(method = RequestMethod.GET,value = "/contest/getContestWiseLeaderBoard/{contestId}")
    public List<UserPointsDTO> getContestWiseLeaderBoard(@PathVariable("contestId") String contestId)
    {
        return userPointsServiceInterface.getByContestId(contestId);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/contest/updateRank/{contestId}")
    public List<UserPointsDTO> updateRank(@PathVariable("contestId") String contestId)
    {
        List<UserPointsDTO> userPointsDTOList =  userPointsServiceInterface.getByContestId(contestId);
        Collections.sort(userPointsDTOList,Collections.reverseOrder(new SortbyFinalPoints()));
        int index = 1;
        for(UserPointsDTO userPointsDTO:userPointsDTOList)
        {
            userPointsDTO.setRank(index);
            index++;
            userPointsServiceInterface.save(userPointsDTO);
        }
        return userPointsDTOList;
    }

}

class SortbyFinalPoints implements Comparator<UserPointsDTO>
{
    public int compare(UserPointsDTO a, UserPointsDTO b)
    {
        return a.getFinalPoints() - b.getFinalPoints();
    }
}



