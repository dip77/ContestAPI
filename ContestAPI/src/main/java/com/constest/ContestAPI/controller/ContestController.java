package com.constest.ContestAPI.controller;

import com.constest.ContestAPI.dto.*;
import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.ContestQuestionEntity;
import com.constest.ContestAPI.entity.UserAnswerEntity;
import com.constest.ContestAPI.service.ContestQuestionService;
import com.constest.ContestAPI.service.UserAnswerService;
import com.constest.ContestAPI.service.UserPointsService;
import com.constest.ContestAPI.service.impl.ContestServiceImpl;
import com.constest.ContestAPI.util.ValidationUtil;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private UserPointsService userPointsService;

    private int bonus = 5;

    @RequestMapping(method = RequestMethod.POST, value = "/createContest")
    public ContestDTO saveContest(@RequestBody ContestDTO contestDTO) {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setContestType(contestDTO.getContestType().toLowerCase());
        BeanUtils.copyProperties(contestDTO, contestEntity);
        contestEntity = contestService.saveContest(contestEntity);
        ContestDTO contestDTO1=new ContestDTO();
        BeanUtils.copyProperties(contestEntity,contestDTO1);
        return contestDTO1;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    public List<ContestDTO> getAllContest() {

        List<ContestEntity> contestEntityList = contestService.getAll();
        List<ContestDTO> contestDTOList = new ArrayList<ContestDTO>();
        for (ContestEntity contestEntity : contestEntityList) {
            ContestDTO contestDTO = new ContestDTO();
            if (!ValidationUtil.compare(contestEntity.getEndDate())) {
                continue;
            }
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

    @RequestMapping(method = RequestMethod.GET, value = "/getContestsByType/{contestType}")
    public List<ContestDTO> getContestsByType(@PathVariable("contestType") String contestType) {
        List<ContestEntity> contestEntityList = contestService.getAllByContestType(contestType);
        List<ContestDTO> contestDTOList = new ArrayList<ContestDTO>();
        for (ContestEntity contestEntity : contestEntityList) {
            if (!ValidationUtil.compare(contestEntity.getEndDate())) {
                continue;
            }

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
            UserAnswerDTO userAnswerDTO = new UserAnswerDTO();
            UserAnswerEntity userAnswerEntity = userAnswerService.getUserEntity(userId, contestQuestionEntity.getContestQuestionId());
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

    @RequestMapping(method = RequestMethod.GET, value = "/getContestPoints/{contestId}/{userId}")
    public Boolean getContestPoints(@PathVariable("contestId") String contestId, @PathVariable("userId") String userId) {
        System.out.println("get points()");
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
        UserPointsDTO userPointsDTO = new UserPointsDTO();
        //this function will call API of Question microservice

        int count = 0, points = 0, easyCorrectlyAnswered = 0, mediumCorrectlyAnswered = 0, hardCorrectlyAnswered = 0, flag = 0;
        for (ContestQuestionEntity contestQuestionEntity : contestEntity.getContestQuestionEntityList()) {
            ContestQuestionDTO contestQuestionDTO = new ContestQuestionDTO();
            BeanUtils.copyProperties(contestQuestionEntity, contestQuestionDTO);
            UserAnswerDTO userAnswerDTO = new UserAnswerDTO();
            System.out.println("userId" + userId);
            System.out.println("contest question entity" + contestQuestionEntity);
            UserAnswerEntity userAnswerEntity = userAnswerService.getUserEntity(userId, contestQuestionEntity.getContestQuestionId());
            if (userAnswerEntity != null) {
                int point = userAnswerEntity.getPoints();
                switch (point) {
                    case 1:
                        easyCorrectlyAnswered++;
                        break;
                    case 2:
                        mediumCorrectlyAnswered++;
                        break;
                    case 3:
                        hardCorrectlyAnswered++;
                        break;
                    default:
                        flag = 1;
                        break;
                }
                points += userAnswerEntity.getPoints();
                contestQuestionDTO.setUserAnswerDTO(userAnswerDTO);
            }
            count++;
        }
        System.out.println("points" + points);
        userPointsDTO.setEasyCorrectlyAnswered(easyCorrectlyAnswered);
        userPointsDTO.setHardCorrectlyAnswered(hardCorrectlyAnswered);
        userPointsDTO.setMediumCorrectlyAnswered(mediumCorrectlyAnswered);
        userPointsDTO.setUserId(userId);
        userPointsDTO.setContestDTO(contestDTO);
        if (flag == 0) {
            userPointsDTO.setBonus(contestDTO.getBonus());
        } else {
            userPointsDTO.setBonus(0);
        }
        userPointsDTO.setFinalPoints(points + userPointsDTO.getBonus());
        userPointsService.save(userPointsDTO);
        return true;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/getContestByAdminId/{adminId}")
    public List<ContestDTO> getContestsByTAdmin(@PathVariable("adminId") String adminId) {
        List<ContestEntity> contestEntityList = contestService.getContestByAdmin(adminId);
        List<ContestDTO> contestDTOList = new ArrayList<ContestDTO>();
        for (ContestEntity contestEntity : contestEntityList) {
            ContestDTO contestDTO = new ContestDTO();
            BeanUtils.copyProperties(contestEntity, contestDTO);
            contestDTOList.add(contestDTO);
        }
        return contestDTOList;

    }

    public QuestionDTO getQuestion(String questionId) {
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
            return rs.getBody();
        }

        return null;

    }

}
