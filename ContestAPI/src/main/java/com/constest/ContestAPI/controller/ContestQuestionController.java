package com.constest.ContestAPI.controller;

import com.constest.ContestAPI.dto.ContestDTO;
import com.constest.ContestAPI.dto.ContestQuestionDTO;
import com.constest.ContestAPI.dto.QuestionDTO;
import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.ContestQuestionEntity;
import com.constest.ContestAPI.service.ContestQuestionService;
import com.constest.ContestAPI.service.impl.FCMService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/contestQuestion")
public class ContestQuestionController {

    @Autowired
    private ContestQuestionService contestQuestionService;

    @RequestMapping(method = RequestMethod.POST, value = "/addQuestion")
    public Boolean saveQuestion(@RequestBody ContestQuestionDTO contestQuestionDTO) {
        System.out.println(contestQuestionDTO);
        ContestQuestionEntity contestQuestionEntity = new ContestQuestionEntity();
        BeanUtils.copyProperties(contestQuestionDTO, contestQuestionEntity);
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setContestId(contestQuestionDTO.getContestDTO().getContestId());
        contestQuestionEntity.setContestEntity(contestEntity);
        return contestQuestionService.saveQuestion(contestQuestionEntity);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addQuestions")
    public Boolean saveQuestion(@RequestBody List<ContestQuestionDTO> contestQuestionDTOList) {
        List<ContestQuestionEntity> contestQuestionEntityList = new ArrayList<ContestQuestionEntity>();
        for (ContestQuestionDTO contestQuestionDTO : contestQuestionDTOList) {
            ContestQuestionEntity contestQuestionEntity = new ContestQuestionEntity();
            BeanUtils.copyProperties(contestQuestionDTO, contestQuestionEntity);
            contestQuestionEntityList.add(contestQuestionEntity);
        }
        return contestQuestionService.saveQuestions(contestQuestionEntityList);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pushQuestion/{contestQuestionId}")
    public Boolean pushQuestion(@PathVariable("contestQuestionId") String contestQuestionId) {
        ContestQuestionEntity contestQuestionEntity = contestQuestionService.getContestQuestionById(contestQuestionId);
        QuestionDTO questionDTO = this.getQuestion(contestQuestionEntity.getQuestionId());
        FCMService fcmService = new FCMService();
        ContestQuestionDTO contestQuestionDTO = new ContestQuestionDTO();
        BeanUtils.copyProperties(contestQuestionEntity, contestQuestionDTO);
        contestQuestionDTO.setQuestionDTO(questionDTO);
        String msg = fcmService.postQuestionToUsers(contestQuestionEntity.getContestEntity().getContestId(), contestQuestionDTO);
        System.out.println(msg);
        return true;


    }

    @RequestMapping(method = RequestMethod.GET, value = "/getContestQuestion/{contestQuestionId}")
    public ContestQuestionDTO getContestQuestion(@PathVariable("contestQuestionId") String contestQuestionId) {
        ContestQuestionEntity contestQuestionEntity = contestQuestionService.getContestQuestionById(contestQuestionId);
        System.out.println(contestQuestionEntity);
        ContestDTO contestDTO = new ContestDTO();
        BeanUtils.copyProperties(contestQuestionEntity.getContestEntity(), contestDTO);
        ContestQuestionDTO contestQuestionDTO = new ContestQuestionDTO();
        BeanUtils.copyProperties(contestQuestionEntity, contestQuestionDTO);
        contestQuestionDTO.setContestDTO(contestDTO);
        return contestQuestionDTO;
    }

    public QuestionDTO getQuestion(String questionId) {
        System.out.println(questionId);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("questionId", questionId);
        String URL = "http://10.177.2.15:8080/question/getOne/" + questionId;
        HttpEntity<Object> entity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<QuestionDTO> rs = restTemplate.exchange(URL, HttpMethod.GET,
                entity, new ParameterizedTypeReference<QuestionDTO>() {
                });
        if (rs.getStatusCode() == HttpStatus.OK) {
            System.out.println(restTemplate.getUriTemplateHandler().toString());
            return rs.getBody();
        }

        return null;

    }

}
