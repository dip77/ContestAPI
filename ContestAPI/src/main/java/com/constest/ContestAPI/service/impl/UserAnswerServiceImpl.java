package com.constest.ContestAPI.service.impl;

import com.constest.ContestAPI.dto.QuestionDTO;
import com.constest.ContestAPI.dto.UserAnswerDTO;
import com.constest.ContestAPI.entity.ContestQuestionEntity;
import com.constest.ContestAPI.entity.UserAnswerEntity;
import com.constest.ContestAPI.repository.UserAnswerRepository;
import com.constest.ContestAPI.service.UserAnswerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service

public class UserAnswerServiceImpl implements UserAnswerService {

    @Autowired
    UserAnswerRepository userAnswerRepository;

    @Override
    public Boolean save(UserAnswerDTO userAnswerDTO) {
        UserAnswerEntity userAnswerEntity = new UserAnswerEntity();
        BeanUtils.copyProperties(userAnswerDTO, userAnswerEntity);
        ContestQuestionEntity contestQuestionEntity = new ContestQuestionEntity();
        contestQuestionEntity.setContestQuestionId(userAnswerDTO.getContestQuestionDTO().getContestQuestionId());
        userAnswerEntity.setContestQuestionEntity(contestQuestionEntity);

        System.out.println(userAnswerDTO.getContestQuestionDTO().getQuestionDTO());
        userAnswerEntity.setTimeOfAnswer(String.valueOf(System.currentTimeMillis()));
        userAnswerEntity.setPoints(Integer.parseInt(checkAnswer(userAnswerDTO.getContestQuestionDTO().getQuestionId(), userAnswerEntity.getAnswer().toUpperCase())));
        System.out.println(userAnswerEntity);
        userAnswerRepository.save(userAnswerEntity);
        return true;
    }


    @Override
    public Optional<UserAnswerEntity> getSingleUser(String userAnswerId) {
        Optional<UserAnswerEntity> userAnswerEntity = userAnswerRepository.findById(userAnswerId);
        return userAnswerEntity;
    }

    @Override
    public List<UserAnswerEntity> getUserAllAnswers(String userId) {
        List<UserAnswerEntity> userAnswerEntities = (List<UserAnswerEntity>) userAnswerRepository.findAllByUserId(userId);
        return userAnswerEntities;
    }

    @Override
    public UserAnswerEntity getUserEntity(String userId, String contestQuestionId) {

        ContestQuestionEntity contestQuestionEntity = new ContestQuestionEntity();
        contestQuestionEntity.setContestQuestionId(contestQuestionId);
        Boolean checkIfExists = userAnswerRepository.existsByUserIdAndContestQuestionEntity(userId, contestQuestionEntity);
        if (checkIfExists == false) {
            return null;
        }
        UserAnswerEntity userAnswerEntities = userAnswerRepository.getOneByUserIdAndContestQuestionEntity(userId, contestQuestionEntity);
        return userAnswerEntities;
    }


    @Override
    public String getFastestAnswer(String customQuesionId) {
        String userAnswerId = userAnswerRepository.getFastestTime(customQuesionId);
        System.out.println(userAnswerId);
        return userAnswerId;
    }

    @Override
    public String checkAnswer(String questionId, String answer) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String URL = "http://10.177.1.100:8080/question/checkAnswer/" + questionId + "/" + answer;
        HttpEntity<Object> entity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<String> rs = restTemplate.exchange(URL, HttpMethod.GET,
                entity, new ParameterizedTypeReference<String>() {
                });
        if (rs.getStatusCode() == HttpStatus.OK) {
            System.out.println(restTemplate.getUriTemplateHandler().toString());
            return (rs.getBody());
        }

        return null;

    }

}