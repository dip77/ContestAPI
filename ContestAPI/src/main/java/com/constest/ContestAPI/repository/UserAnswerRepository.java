package com.constest.ContestAPI.repository;

import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.ContestQuestionEntity;
import com.constest.ContestAPI.entity.UserAnswerEntity;
//import com.constest.ContestAPI.entity.UserPointsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends CrudRepository<UserAnswerEntity, String> {


    //public List<UserAnswerEntity> findAllByContestQuestionIdAndUserId(String contestQuestionId,String userId);

    public List<UserAnswerEntity> findAllByUserId(String userId);

    public List<UserAnswerEntity> getByContestQuestionEntity(ContestQuestionEntity contestQuestionEntity);

    //  public List<UserAnswerEntity> findAllByContestQuestionEntityAndUserId(ContestQuestionEntity contestQuestionEntity,String userId);

   // public List<UserAnswerEntity> findAllByUserPointsEntityAndUserId(UserPointsEntity userPointsEntity,String userId);

    public UserAnswerEntity getOneByUserIdAndContestQuestionEntity(String userId,ContestQuestionEntity contestQuestionEntity);
}

