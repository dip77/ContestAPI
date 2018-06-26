package com.constest.ContestAPI.controller;

import com.constest.ContestAPI.dto.ContestDTO;
import com.constest.ContestAPI.dto.ContestQuestionDTO;
import com.constest.ContestAPI.dto.QuestionDTO;
import com.constest.ContestAPI.dto.UserAnswerDTO;
import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.ContestQuestionEntity;
import com.constest.ContestAPI.entity.UserAnswerEntity;
import com.constest.ContestAPI.service.UserAnswerService;
import com.constest.ContestAPI.service.impl.ContestServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contest")
public class ContestController {

    @Autowired
    private ContestServiceImpl contestService;

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


    @RequestMapping(method = RequestMethod.GET, value = "/getContestQuestions/{contestId}")
    public ContestDTO getContestQuestions(@PathVariable("contestId") String contestId) {
        ContestEntity contestEntity = contestService.getAllContestQuestions(contestId);
        ContestDTO contestDTO = new ContestDTO();
        BeanUtils.copyProperties(contestEntity, contestDTO);
        List<ContestQuestionDTO> contestQuestionDTOList = new ArrayList<ContestQuestionDTO>();
        //this function will call API of Question microservice
        List<QuestionDTO> questionDTOList = this.getQuestions(contestEntity.getContestQuestionEntityList());
        System.out.println(contestEntity.getContestQuestionEntityList().size() + " size contest question");
        System.out.println(questionDTOList.size() + " size question dto list");

        int count = 0;
        //todo : phani : based on the question id, select the data from the questionDTOList and add to the list.
        // todo : use hash map here .. instead of array list, or change the call to get by each question
        for (ContestQuestionEntity contestQuestionEntity : contestEntity.getContestQuestionEntityList()) {
            ContestQuestionDTO contestQuestionDTO = new ContestQuestionDTO();
            BeanUtils.copyProperties(contestQuestionEntity, contestQuestionDTO);
            System.out.println(contestQuestionDTO.getContestQuestionId());
           // QuestionDTO questionDTO = new QuestionDTO();
//            UserAnswerDTO userAnswerDTO = new UserAnswerDTO();
//            UserAnswerEntity userAnswerEntity = userAnswerService.getUserEntity(userId, contestQuestionDTO.getContestQuestionId());
//            if (userAnswerEntity != null)
//                BeanUtils.copyProperties(userAnswerEntity, userAnswerDTO);
//            contestQuestionDTO.setUserAnswerDTO(userAnswerDTO);


            if (count < questionDTOList.size())
                contestQuestionDTO.setQuestionDTO(questionDTOList.get(count));
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

    public List<QuestionDTO> getQuestions(List<ContestQuestionEntity> contestQuestionEntityList) {
        List<QuestionDTO> questionDTOList = null;
        List<String> questionIds = new ArrayList<String>();
        Boolean returnVal;
        for (ContestQuestionEntity contestQuestionEntity : contestQuestionEntityList) {
            questionIds.add(contestQuestionEntity.getQuestionId());
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String URL = "http://10.177.2.15:8080/question/getQuestions";
        HttpEntity<Object> entity = new HttpEntity<Object>(questionIds, httpHeaders);
        ResponseEntity<List<QuestionDTO>> rs = restTemplate.exchange(URL, HttpMethod.POST,
                entity, new ParameterizedTypeReference<List<QuestionDTO>>() {
                });
        if (rs.getStatusCode() == HttpStatus.OK) {
            returnVal = true;
            questionDTOList = rs.getBody();
            //   System.out.println("YEs"+questionDTOList);
            //UrlEntity urlEntity = new UrlEntity();
        } else {
            System.out.println("failed");
            returnVal = false;
        }


        return questionDTOList;

    }

}
