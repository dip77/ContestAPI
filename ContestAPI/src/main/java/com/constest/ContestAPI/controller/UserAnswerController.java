package com.constest.ContestAPI.controller;

import com.constest.ContestAPI.dto.UserAnswerDTO;
import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.UserAnswerEntity;
import com.constest.ContestAPI.service.UserPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contest/")
public class UserAnswerController {

    @Autowired
    UserPointsService userPointsService;

    @RequestMapping(method = RequestMethod.POST, value = "save")
    public Boolean userAnswer(@RequestBody UserAnswerDTO userAnswerDTO) {
        Boolean userAnswerDTO1 = userPointsService.save(userAnswerDTO);
        return userAnswerDTO1;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAnswer/{userAnswerId}")
    public String getAnswer(@PathVariable String userAnswerId) {
        return (userPointsService.getAnswer(userAnswerId));
    }


    @RequestMapping(method = RequestMethod.GET, value = "getAllAnswers/{userId}")
    public List<UserAnswerEntity> getAllAnswers(@PathVariable String userId) {
        return userPointsService.getUserAllAnswers(userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getUserPoints/{userId}")
    public Integer getUserPoints(@PathVariable String userId) {
        return userPointsService.getUserScore(userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getFastestUser/{contestQuestionId}")
    public String getFastestUser(@PathVariable String contestQuestionId) {
        return userPointsService.getFastestAnswer(contestQuestionId);
    }

    // @RequestMapping(method = RequestMethod.GET, value = "getUserContestPoints/{contestId}/{userId}")
    // public Integer getUserContestPoints(@PathVariable String contestId, @PathVariable String userId) {
    //     return userPointsService.getUserContestPoints(contestId, userId);
    // }


}
