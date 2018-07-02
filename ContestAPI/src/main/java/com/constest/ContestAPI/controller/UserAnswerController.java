package com.constest.ContestAPI.controller;

import com.constest.ContestAPI.dto.UserAnswerDTO;
import com.constest.ContestAPI.entity.UserAnswerEntity;
import com.constest.ContestAPI.service.UserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userAnswers/")
public class UserAnswerController {

    @Autowired
    UserAnswerService userAnswerService;

    @RequestMapping(method = RequestMethod.POST, value = "saveAnswer")
    public Boolean userAnswer(@RequestBody UserAnswerDTO userAnswerDTO) {
        System.out.println("save answer "+userAnswerDTO);
        Boolean userAnswerDTO1 = userAnswerService.save(userAnswerDTO);
        return userAnswerDTO1;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getUserEntity/{userId}/{contestQuestionId}")
    public UserAnswerEntity getUserEntity(@PathVariable String userId, @PathVariable String contestQuestionId) {
        return userAnswerService.getUserEntity(userId, contestQuestionId);
    }
}