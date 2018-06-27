package com.constest.ContestAPI.controller;

import com.constest.ContestAPI.dto.UserAnswerDTO;
import com.constest.ContestAPI.entity.UserAnswerEntity;
import com.constest.ContestAPI.service.UserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//todo : phani .. change the path from contest to userAnswers etc..
@RequestMapping("/userAnswers/")
public class UserAnswerController {

    @Autowired
    UserAnswerService userAnswerService;

    @RequestMapping(method = RequestMethod.POST, value = "saveAnswer")
    public Boolean userAnswer(@RequestBody UserAnswerDTO userAnswerDTO) {
        Boolean userAnswerDTO1 = userAnswerService.save(userAnswerDTO);
        return userAnswerDTO1;
    }

    // todo : phani : remove this method
    @RequestMapping(method = RequestMethod.GET, value = "{userAnswerId}")
    public String getAnswer(@PathVariable String userAnswerId) {
        return (userAnswerService.getAnswer(userAnswerId));
    }


    @RequestMapping(method = RequestMethod.GET, value = "getAllAnswers/{userId}")
    public List<UserAnswerEntity> getAllAnswers(@PathVariable String userId) {
        return userAnswerService.getUserAllAnswers(userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getUserPoints/{userId}")
    public Integer getUserPoints(@PathVariable String userId) {
        return userAnswerService.getUserScore(userId);
    }

    //todo : phani : not to use this method outside
//    @RequestMapping(method = RequestMethod.GET, value = "getFastestUser/{contestQuestionId}")
//    public String getFastestUser(@PathVariable String contestQuestionId) {
//        return userAnswerService.getFastestAnswer(contestQuestionId);
//    }


    @RequestMapping(method = RequestMethod.GET, value = "getUserEntity/{userId}/{contestQuestionId}")
    public UserAnswerEntity getUserEntity(@PathVariable String userId,@PathVariable String contestQuestionId) {
        return userAnswerService.getUserEntity(userId,contestQuestionId);
    }

    // @RequestMapping(method = RequestMethod.GET, value = "getUserContestPoints/{contestId}/{userId}")
    // public Integer getUserContestPoints(@PathVariable String contestId, @PathVariable String userId) {
    //     return userAnswerService.getUserContestPoints(contestId, userId);
    // }

//    @RequestMapping(method = RequestMethod.GET,value = "test/{customQuesionId}")
//    public  Boolean test(@PathVariable String customQuesionId){
//        userAnswerService.getFastestAnswer(customQuesionId);
//        return true;
//    }



}
