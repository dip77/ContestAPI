package com.constest.ContestAPI.service.impl;

import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.ContestQuestionEntity;
import com.constest.ContestAPI.repository.ContestQuestionRepository;
import com.constest.ContestAPI.service.ContestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestQuestionServiceImpl implements ContestQuestionService {

    @Autowired
    private ContestQuestionRepository contestQuestionRepository;


    @Override
    public Boolean saveQuestion(ContestQuestionEntity contestQuestionEntity) {
        return contestQuestionRepository.save(contestQuestionEntity)!=null;
    }

    @Override
    public Boolean saveQuestions(List<ContestQuestionEntity> contestQuestionEntityList) {
        return contestQuestionRepository.saveAll(contestQuestionEntityList)!=null;
    }

    @Override
    public ContestQuestionEntity getContestQuestionById(String contestQuestionId) {
        return contestQuestionRepository.findById(contestQuestionId).get();
    }

    @Override
    public Boolean isContestExists(ContestEntity contestEntity) {
        return contestQuestionRepository.existsByContestEntity(contestEntity);
    }
}
