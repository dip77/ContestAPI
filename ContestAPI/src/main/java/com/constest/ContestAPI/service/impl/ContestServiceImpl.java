package com.constest.ContestAPI.service.impl;

import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.ContestQuestionEntity;
import com.constest.ContestAPI.repository.ContestRepository;
import com.constest.ContestAPI.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {


    @Autowired
    private ContestRepository contestRepository;

    @Override
    public List<ContestEntity> getAllByCategory(String categoryid) {
        return contestRepository.findByCategoryId(categoryid);
    }


    @Override
    public List<ContestEntity> getAllByContestType(String contestType) {
        return contestRepository.findByContestType(contestType);
    }

    @Override
    public Boolean saveContest(ContestEntity contestEntity) {
            return contestRepository.save(contestEntity) != null;
    }

    @Override
    public List<ContestEntity> getAll() {
        return (List<ContestEntity>) contestRepository.findAll();
    }

    @Override
    public List<ContestEntity> getContestByAdmin(String adminId) {
      return   contestRepository.findAllByAdminId(adminId);
    }

    @Override
    public ContestEntity getAllContestQuestions(String contestId) {

        return contestRepository.findById(contestId).get();
    }
}
