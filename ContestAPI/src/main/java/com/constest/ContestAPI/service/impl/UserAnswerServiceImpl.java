package com.constest.ContestAPI.service.impl;

import com.constest.ContestAPI.dto.UserAnswerDTO;
import com.constest.ContestAPI.entity.ContestEntity;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

public class UserAnswerServiceImpl implements UserAnswerService {

    Integer points;

    @Autowired
    UserAnswerRepository userAnswerRepository;

    @Autowired
    UserPointsServiceImpl userPointsServiceImpl;

    @Override
    public Boolean save(UserAnswerDTO userAnswerDTO) {
        System.out.println("save called " + userAnswerDTO);
        UserAnswerEntity userAnswerEntity = new UserAnswerEntity();
        BeanUtils.copyProperties(userAnswerDTO, userAnswerEntity);
        ContestQuestionEntity contestQuestionEntity = new ContestQuestionEntity();
        contestQuestionEntity.setContestQuestionId(userAnswerDTO.getContestQuestionDTO().getContestQuestionId());
        userAnswerEntity.setContestQuestionEntity(contestQuestionEntity);
        userAnswerEntity.setTimeOfAnswer(String.valueOf(System.currentTimeMillis()));
        ContestEntity contestEntity = new ContestEntity();
        System.out.println(userAnswerDTO);
        contestEntity.setContestId(userAnswerDTO.getContestQuestionDTO().getContestDTO().getContestId());
        contestEntity.setContestType(userAnswerDTO.getContestQuestionDTO().getContestDTO().getContestType());
        userAnswerEntity.getContestQuestionEntity().setContestEntity(contestEntity);
        String time = String.valueOf((System.currentTimeMillis())).substring(3);
        userAnswerEntity.setTimeOfAnswer(time);
        String points = "0";

        if (userAnswerEntity.getAnswer() != null) {
            System.out.println("Inside getAnswer");
            points = checkAnswer(userAnswerDTO.getContestQuestionDTO().getQuestionId(), userAnswerEntity.getAnswer().toUpperCase());
            if (points != null) {
                if (userAnswerEntity.getContestQuestionEntity().getContestEntity().getContestType().equalsIgnoreCase("static")) {
                    userAnswerEntity.setPoints(Integer.parseInt(points));
                } else {
                    if (points.equals("0")) {
                        userAnswerEntity.setTimeOfAnswer(null);
                    }
                }
            }


            userAnswerRepository.save(userAnswerEntity);
        }
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
    public List<String> getFastestAnswer(String contestQuestionId) {
        List<String> userName = new ArrayList<>();
        List<String> userAnswerIds = userAnswerRepository.getFastestTime(contestQuestionId);
        if (userAnswerIds.size() != 0) {
            int index = 0;
            for (index = 0; index < userAnswerIds.size(); index++) {
                if (userAnswerIds.get(index) != null) {
                    UserAnswerEntity userAnswerEntity = new UserAnswerEntity();
                    userAnswerEntity = userAnswerRepository.findById(userAnswerIds.get(index)).get();
                    System.out.println("\nQuestion Id" + userAnswerEntity.getContestQuestionEntity().getQuestionId() + "\n" + userAnswerEntity.getAnswer().toUpperCase());
                    String points = checkAnswer(userAnswerEntity.getContestQuestionEntity().getQuestionId(), userAnswerEntity.getAnswer().toUpperCase());
                    userAnswerEntity.setPoints(Integer.parseInt(points));
                    userAnswerRepository.save(userAnswerEntity);
                    userName.add(userPointsServiceImpl.getUserName(userAnswerEntity.getUserId()));
                }
            }
        }
        return userName;
    }

    @Override
    public String checkAnswer(String questionId, String answer) {
        System.out.println(questionId + " - " + answer);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String URL = "http://10.177.2.201:8081/question/checkAnswer/" + questionId + "/" + answer;
        HttpEntity<Object> entity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<String> rs = restTemplate.exchange(URL, HttpMethod.GET,
                entity, new ParameterizedTypeReference<String>() {
                });
        if (rs.getStatusCode() == HttpStatus.OK) {
            return (rs.getBody());
        }
        return null;
    }
}