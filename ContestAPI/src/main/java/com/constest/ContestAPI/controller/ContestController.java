package com.constest.ContestAPI.controller;

import com.constest.ContestAPI.dto.ContestDTO;
import com.constest.ContestAPI.dto.ContestQuestionDTO;
import com.constest.ContestAPI.dto.QuestionDTO;
import com.constest.ContestAPI.dto.UserAnswerDTO;
import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.ContestQuestionEntity;
import com.constest.ContestAPI.entity.UserAnswerEntity;
import com.constest.ContestAPI.service.ContestQuestionService;
import com.constest.ContestAPI.service.UserAnswerService;
import com.constest.ContestAPI.service.impl.ContestServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/contest")
public class ContestController {

    @Autowired
    private ContestServiceImpl contestService;

    @Autowired
    private ContestQuestionService contestQuestionService;

    @Autowired
    private UserAnswerService userAnswerService;

    @RequestMapping(method = RequestMethod.POST, value = "/createContest")
    public Boolean saveContest(@RequestBody ContestDTO contestDTO) {
        ContestEntity contestEntity = new ContestEntity();

        contestEntity.setContestType(contestDTO.getContestType().toLowerCase());
        BeanUtils.copyProperties(contestDTO, contestEntity);
        return contestService.saveContest(contestEntity);
    }

    //todo : Phani : Filter inactive contests in this api, get only active contests
    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    public List<ContestDTO> getAllContest() {


        List<ContestEntity> contestEntityList = contestService.getAll();
        List<ContestDTO> contestDTOList = new ArrayList<ContestDTO>();
        for (ContestEntity contestEntity : contestEntityList) {
            ContestDTO contestDTO = new ContestDTO();
            //  System.out.println(contestEntity.getContestQuestionEntityList());
            List<ContestQuestionDTO> contestQuestionDTOList = new ArrayList<ContestQuestionDTO>();
            BeanUtils.copyProperties(contestEntity, contestDTO);
            for (ContestQuestionEntity contestQuestionEntity : contestEntity.getContestQuestionEntityList()) {
                ContestQuestionDTO contestQuestionDTO = new ContestQuestionDTO();
                BeanUtils.copyProperties(contestQuestionEntity, contestQuestionDTO);
                contestQuestionDTOList.add(contestQuestionDTO);
            }
            contestDTO.setContestQuestionDTOList(contestQuestionDTOList);
            contestDTOList.add(contestDTO);
        }
        return contestDTOList;
    }


    //todo : phani : get the active contest only
    @RequestMapping(method = RequestMethod.GET, value = "/getContestsByCategory/{categoryId}")
    public List<ContestDTO> getContestsByCategory(@PathVariable("categoryId") String categoryId) {
        List<ContestEntity> contestEntityList = contestService.getAllByCategory(categoryId);
        List<ContestDTO> contestDTOList = new ArrayList<ContestDTO>();
        for (ContestEntity contestEntity : contestEntityList) {
            ContestDTO contestDTO = new ContestDTO();
            BeanUtils.copyProperties(contestEntity, contestDTO);
            contestDTOList.add(contestDTO);
        }
        return contestDTOList;
    }

    //todo : phani : get the active contest only
    @RequestMapping(method = RequestMethod.GET, value = "/getContestsByType/{contestType}")
    public List<ContestDTO> getContestsByType(@PathVariable("contestType") String contestType) {
        List<ContestEntity> contestEntityList = contestService.getAllByContestType(contestType);
        List<ContestDTO> contestDTOList = new ArrayList<ContestDTO>();
        for (ContestEntity contestEntity : contestEntityList) {
            ContestDTO contestDTO = new ContestDTO();
            BeanUtils.copyProperties(contestEntity, contestDTO);
            contestDTOList.add(contestDTO);
        }
        return contestDTOList;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/getContestQuestions/{contestId}/{userId}")
    public ContestDTO getContestQuestions(@PathVariable("contestId") String contestId, @PathVariable("userId") String userId) {

        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setContestId(contestId);
        boolean isContestExists = contestQuestionService.isContestExists(contestEntity);

        if (!isContestExists) {
            return null;
        }

        contestEntity = contestService.getAllContestQuestions(contestId);
        ContestDTO contestDTO = new ContestDTO();
        BeanUtils.copyProperties(contestEntity, contestDTO);
        List<ContestQuestionDTO> contestQuestionDTOList = new ArrayList<ContestQuestionDTO>();
        //this function will call API of Question microservice

        int count = 0;
        for (ContestQuestionEntity contestQuestionEntity : contestEntity.getContestQuestionEntityList()) {
            ContestQuestionDTO contestQuestionDTO = new ContestQuestionDTO();
            BeanUtils.copyProperties(contestQuestionEntity, contestQuestionDTO);
            System.out.println(contestQuestionDTO.getContestQuestionId());
            UserAnswerDTO userAnswerDTO = new UserAnswerDTO();
            UserAnswerEntity userAnswerEntity = userAnswerService.getUserEntity(userId, contestQuestionEntity.getContestQuestionId());
            System.out.println(userAnswerEntity + " user");
            if (userAnswerEntity != null) {
                BeanUtils.copyProperties(userAnswerEntity, userAnswerDTO);
                contestQuestionDTO.setUserAnswerDTO(userAnswerDTO);
            }


            contestQuestionDTO.setQuestionDTO(this.getQuestion(contestQuestionEntity.getQuestionId()));

            count++;
            contestQuestionDTOList.add(contestQuestionDTO);
        }
        contestDTO.setContestQuestionDTOList(contestQuestionDTOList);
        return contestDTO;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/getContestByAdminId/{adminId}")
    public ContestDTO getContestsByTAdmin(@PathVariable("adminId") String adminId) {
        ContestEntity contestEntity = contestService.getContestByAdmin(adminId);
        ContestDTO contestDTO = new ContestDTO();
        if (contestEntity == null)
            return contestDTO;
        BeanUtils.copyProperties(contestEntity, contestDTO);
        return contestDTO;

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
