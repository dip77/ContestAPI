package com.constest.ContestAPI.repository;

import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.UserPointsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPointsRepository extends CrudRepository<UserPointsEntity,String> {

    List<UserPointsEntity> findAllByUserIdAndFinalPoints(String userId, int finalPoints);
    List<UserPointsEntity> findAllByContestEntity(ContestEntity contestEntity);

}
