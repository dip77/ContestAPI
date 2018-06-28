package com.constest.ContestAPI.repository;

import com.constest.ContestAPI.entity.ContestQuestionEntity;
import com.constest.ContestAPI.entity.UserAnswerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAnswerRepository extends CrudRepository<UserAnswerEntity, String> {


    public List<UserAnswerEntity> findAllByUserId(String userId);

    public List<UserAnswerEntity> getByContestQuestionEntity(ContestQuestionEntity contestQuestionEntity);

    public UserAnswerEntity getOneByUserIdAndContestQuestionEntity(String userId, ContestQuestionEntity contestQuestionEntity);

    @Query(nativeQuery = true, value = "select user_answer_id from user_answer where contest_question_id = ?#{[0]} and time_of_answer = (select min(time_of_answer) from user_answer)  ")
    String getFastestTime(String contest_question_id);

    public Boolean existsByUserIdAndContestQuestionEntity(String userId, ContestQuestionEntity contestQuestionEntity);


}

