package com.constest.ContestAPI.controller;

import com.constest.ContestAPI.dto.ContestRulesDTO;
import com.constest.ContestAPI.entity.ContestRulesEntity;
import com.constest.ContestAPI.service.ContestRulesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/contest")
public class RulesController {

    @Autowired
    ContestRulesService contestRulesService;

    @RequestMapping(method = RequestMethod.GET,value="/getRules")
    public ResponseEntity<ContestRulesDTO> getRule(){
        ContestRulesEntity contestRulesEntity = contestRulesService.getRule();
        ContestRulesDTO contestRulesDTO = new ContestRulesDTO();
        BeanUtils.copyProperties(contestRulesEntity,contestRulesDTO);
        return new ResponseEntity<>(contestRulesDTO,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value="/setRules")
    public ResponseEntity<ContestRulesDTO> setRule(@RequestBody ContestRulesDTO contestRulesDTO){
        ContestRulesEntity contestRulesEntity = new ContestRulesEntity();
        BeanUtils.copyProperties(contestRulesDTO,contestRulesEntity);
        contestRulesEntity = contestRulesService.setRule(contestRulesEntity);
        BeanUtils.copyProperties(contestRulesEntity,contestRulesDTO);
        return new ResponseEntity<>(contestRulesDTO,HttpStatus.OK);
    }
}