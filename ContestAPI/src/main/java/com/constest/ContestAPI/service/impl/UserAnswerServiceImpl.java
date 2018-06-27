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

//todo : phani : change the name of the class to UserAnswerServiceImpl
public class UserAnswerServiceImpl implements UserAnswerService {

    @Autowired
    UserAnswerRepository userAnswerRepository;

    @Override
    public Boolean save(UserAnswerDTO userAnswerDTO) {
        //todo : phani : need to set the answer time on the backend, not take data from android app
        UserAnswerEntity userAnswerEntity = new UserAnswerEntity();
        BeanUtils.copyProperties(userAnswerDTO, userAnswerEntity);
        ContestQuestionEntity contestQuestionEntity = new ContestQuestionEntity();
        contestQuestionEntity.setContestQuestionId(userAnswerDTO.getContestQuestionDTO().getContestQuestionId());
        userAnswerEntity.setContestQuestionEntity(contestQuestionEntity);
        userAnswerEntity.setTimeOfAnswer(new Timestamp(System.currentTimeMillis()));
        System.out.println(userAnswerEntity);
        //todo : phani .. what is the need of try catch bock here?
        userAnswerRepository.save(userAnswerEntity);
        return true;
    }


    @Override
    public Optional<UserAnswerEntity> getSingleUser(String userAnswerId) {
        Optional<UserAnswerEntity> userAnswerEntity = userAnswerRepository.findById(userAnswerId);
        return userAnswerEntity;
    }

    //todo : remove this method
    @Override
    public String getAnswer(String userAnswerId) {
        UserAnswerEntity userAnswerEntity = userAnswerRepository.findById(userAnswerId).get();
        String answer = userAnswerEntity.getAnswer();
        return answer;
    }

    //todo : phani : remove this, this needs to be done by save method itself
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
    public UserAnswerEntity getUserEntity(String userId, String contestQuestionId) {

        ContestQuestionEntity contestQuestionEntity = new ContestQuestionEntity();
        contestQuestionEntity.setContestQuestionId(contestQuestionId);
        Boolean checkIfExists = userAnswerRepository.existsByUserIdAndContestQuestionEntity(userId,contestQuestionEntity);
        if(checkIfExists == false){
            return null;
        }
        UserAnswerEntity userAnswerEntities = userAnswerRepository.getOneByUserIdAndContestQuestionEntity(userId, contestQuestionEntity);
        return userAnswerEntities;
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

    @Override
    public String getFastestAnswer(String customQuesionId){
        String userAnswerId = userAnswerRepository.getFastestTime(customQuesionId);
        System.out.println(userAnswerId);
        return userAnswerId;
    }


//easy medium hard score
}