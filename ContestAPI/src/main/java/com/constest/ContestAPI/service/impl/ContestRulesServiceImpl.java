package com.constest.ContestAPI.service.impl;

import com.constest.ContestAPI.entity.ContestRulesEntity;
import com.constest.ContestAPI.repository.ContestRulesRepository;
import com.constest.ContestAPI.service.ContestRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContestRulesServiceImpl implements ContestRulesService {

    @Autowired
    ContestRulesRepository contestRulesRepository;

    @Override
    public ContestRulesEntity getRule() {
        return contestRulesRepository.findById("GlobalRule").get();
    }

    @Override
    public ContestRulesEntity setRule(ContestRulesEntity contestRulesEntity) {
        return contestRulesRepository.save(contestRulesEntity);
    }
}
