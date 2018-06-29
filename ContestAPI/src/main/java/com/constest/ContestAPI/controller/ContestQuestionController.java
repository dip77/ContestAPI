package com.constest.ContestAPI.controller;

import com.constest.ContestAPI.dto.ContestDTO;
import com.constest.ContestAPI.dto.ContestQuestionDTO;
import com.constest.ContestAPI.dto.QuestionDTO;
import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.ContestQuestionEntity;
import com.constest.ContestAPI.service.ContestQuestionService;
import com.constest.ContestAPI.service.UserAnswerService;
import com.constest.ContestAPI.service.impl.FCMService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;
import sun.rmi.runtime.Log;

import java.util.*;

@RestController
@RequestMapping(value = "/contestQuestion")
public class ContestQuestionController {

    @Autowired
    private ContestQuestionService contestQuestionService;

    @Autowired
    private UserAnswerService userAnswerService;

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
    public Boolean saveQuestions(@RequestBody List<ContestQuestionDTO> contestQuestionDTOList) {
        System.out.println("add questions");
         List<ContestQuestionEntity> contestQuestionEntityList = new ArrayList<ContestQuestionEntity>();
        for (ContestQuestionDTO contestQuestionDTO : contestQuestionDTOList) {
            System.out.println(contestQuestionDTO.getQuestionId()+" question id");
            ContestQuestionEntity contestQuestionEntity = new ContestQuestionEntity();
            BeanUtils.copyProperties(contestQuestionDTO, contestQuestionEntity);
            ContestEntity contestEntity=new ContestEntity();
            System.out.println(contestQuestionDTO.getContestDTO().getContestId());
            contestEntity.setContestId(contestQuestionDTO.getContestDTO().getContestId());
            contestQuestionEntity.setContestEntity(contestEntity);

            contestQuestionEntityList.add(contestQuestionEntity);
        }
        return contestQuestionService.saveQuestions(contestQuestionEntityList);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pushQuestion/{contestQuestionId}")
    public Boolean pushQuestion(@PathVariable("contestQuestionId") String contestQuestionId) throws InterruptedException {

        System.out.println("push dynamic");
        ContestQuestionEntity contestQuestionEntity = contestQuestionService.getContestQuestionById(contestQuestionId);
        QuestionDTO questionDTO = this.getQuestion(contestQuestionEntity.getQuestionId());
        FCMService fcmService = new FCMService();
        ContestQuestionDTO contestQuestionDTO = new ContestQuestionDTO();
        BeanUtils.copyProperties(contestQuestionEntity, contestQuestionDTO);
        contestQuestionDTO.setQuestionDTO(questionDTO);
        String questionType = questionDTO.getQuestionType();

        String msg = fcmService.postQuestionToUsers(contestQuestionDTO);
        System.out.println(msg);

        int timed = 30000;
        if (!questionType.equals("text") || !questionType.equals("image")) {
            timed = 30000;
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                userAnswerService.getFastestAnswer(contestQuestionId);
                fcmService.postStatusToAdmin(contestQuestionEntity.getContestEntity().getContestId(),"next");
            }
        }, timed);

        System.out.println("answer");
        return true;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/endContest/{contestId}")
    public Boolean pushQuestionEnd(@PathVariable("contestId") String contestId)
    {
        FCMService fcmService = new FCMService();
        fcmService.postStatusToUsers(contestId,"end");
        fcmService.postStatusToAdmin(contestId,"end");
        return Boolean.TRUE;
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
        String URL = "http://10.177.2.201:8081/question/getOne/" + questionId;
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
