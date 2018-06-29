package com.constest.ContestAPI.controller;

import com.constest.ContestAPI.dto.UserAnswerDTO;
import com.constest.ContestAPI.entity.UserAnswerEntity;
import com.constest.ContestAPI.service.UserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userAnswers/")
public class UserAnswerController {

    @Autowired
    UserAnswerService userAnswerService;

    @RequestMapping(method = RequestMethod.POST, value = "saveAnswer")
    public Boolean userAnswer(@RequestBody UserAnswerDTO userAnswerDTO) {
        Boolean userAnswerDTO1 = userAnswerService.save(userAnswerDTO);
        return userAnswerDTO1;
    }

    //todo : phani : not to use this method outside
     @RequestMapping(method = RequestMethod.GET, value = "getFastestUser/{contestQuestionId}")
     public String getFastestUser(@PathVariable String contestQuestionId) throws InterruptedException {
         return userAnswerService.getFastestAnswer(contestQuestionId);
     }


    @RequestMapping(method = RequestMethod.GET, value = "getUserEntity/{userId}/{contestQuestionId}")
    public UserAnswerEntity getUserEntity(@PathVariable String userId, @PathVariable String contestQuestionId) {
        return userAnswerService.getUserEntity(userId, contestQuestionId);
    }




}
