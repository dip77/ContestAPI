package com.constest.ContestAPI.service.impl;

import com.constest.ContestAPI.dto.UserAnswerDTO;
import com.constest.ContestAPI.entity.ContestQuestionEntity;
import com.constest.ContestAPI.entity.UserAnswerEntity;
import com.constest.ContestAPI.repository.UserAnswerRepository;
import com.constest.ContestAPI.service.UserAnswerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service

public class UserAnswerImpl implements UserAnswerService {

    @Autowired
    UserAnswerRepository userAnswerRepository;

    @Override
    public Boolean save(UserAnswerDTO userAnswerDTO) {
        UserAnswerEntity userAnswerEntity = new UserAnswerEntity();
        BeanUtils.copyProperties(userAnswerDTO, userAnswerEntity);
        try {
            userAnswerRepository.save(userAnswerEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<UserAnswerEntity> getSingleUser(String userAnswerId) {
        Optional<UserAnswerEntity> userAnswerEntity = userAnswerRepository.findById(userAnswerId);
        return userAnswerEntity;
    }

    @Override
    public String getAnswer(String userAnswerId) {
        UserAnswerEntity userAnswerEntity = userAnswerRepository.findById(userAnswerId).get();
        String answer = userAnswerEntity.getAnswer();
        return answer;
    }

    @Override
    public Boolean setAnswer(String userAnswerId, String answer) {
        UserAnswerEntity userAnswerEntity = userAnswerRepository.findById(userAnswerId).get();
        userAnswerEntity.setAnswer(answer);
        return true;
    }

    @Override
    public List<UserAnswerEntity> getUserAllAnswers(String userId) {
        List<UserAnswerEntity> userAnswerEntities = (List<UserAnswerEntity>) userAnswerRepository.findAllByUserId(userId);
        return userAnswerEntities;
    }

    @Override
    public Timestamp getAnswerTime(String userAnswerId) {
        UserAnswerEntity userAnswerEntity = userAnswerRepository.findById(userAnswerId).get();
        Timestamp timestamp = userAnswerEntity.getTimeOfAnswer();
        return timestamp;
    }

    @Override
    public Boolean setAnswerTime(String userAnswerId, Timestamp timestamp) {
        UserAnswerEntity userAnswerEntity = userAnswerRepository.findById(userAnswerId).get();
        userAnswerEntity.setTimeOfAnswer(timestamp);
        return true;
    }

//    @Override
//    public Boolean getIfSkipped(String contestQuestionId, String userId) {
//        List<UserAnswerEntity> userAnswerEntities = userAnswerRepository.findAllByContestQuestionIdAndUserId(contestQuestionId, userId);
//        Boolean isSkipped = true;
//        for (UserAnswerEntity userAnswerEntity : userAnswerEntities) {
//            isSkipped &= userAnswerEntity.getSkipped();
//        }
//        return isSkipped;
//    }


    //remove user
    @Override
    public String getFastestAnswer(String contestQuestionId) {
        ContestQuestionEntity contestQuestionEntity = new ContestQuestionEntity();
        contestQuestionEntity.setContestQuestionId(contestQuestionId);
        List<UserAnswerEntity> userAnswerEntities = userAnswerRepository.getByContestQuestionEntity(contestQuestionEntity);
        UserAnswerEntity userAnswerEntity1 = new UserAnswerEntity();//= userAnswerEntities.get(0);
        // userAnswerEntity1.getContestQuestionEntity().getContestEntity();
        Timestamp timestamp = userAnswerEntities.get(0).getTimeOfAnswer();
        String userId = null;
        for (UserAnswerEntity userAnswerEntity : userAnswerEntities) {
            if (timestamp.after(userAnswerEntity.getTimeOfAnswer())) {
                timestamp = userAnswerEntity.getTimeOfAnswer();
                userId = userAnswerEntity.getUserId();
            }
        }
        return userId;
    }


//    @Override
//    public int getUserContestPoints(String contestId, String userId) {
//        ContestQuestionEntity contestQuestionEntity = new ContestQuestionEntity();
//        // contestEntity.setContestId(contestId);
//        UserPointsEntity userPointsEntity = new UserPointsEntity();
//        userPointsEntity.s(contestId);
//        contestQuestionEntity.setContestEntity(contestEntity);
//
//        List<UserAnswerEntity> userAnswerEntities = userAnswerRepository.findAllByUserPointsEntityAndUserId(contestQuestionEntity, userId);
//        int userContestScore = 0;
//        for (UserAnswerEntity userAnswerEntity : userAnswerEntities) {
//            userContestScore += userAnswerEntity.getPoints();
//        }
//        return userContestScore;
//
//    }


    @Override
    public int getUserScore(String userId) {
        List<UserAnswerEntity> userAnswerEntities = userAnswerRepository.findAllByUserId(userId);
        int userScore = 0;
        for (UserAnswerEntity userAnswerEntity : userAnswerEntities) {
            userScore += userAnswerEntity.getPoints();
        }
        return userScore;
    }


    //easy medium hard score

}
