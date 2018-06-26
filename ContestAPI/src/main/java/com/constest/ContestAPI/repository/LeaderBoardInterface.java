package com.constest.ContestAPI.repository;

import com.constest.ContestAPI.entity.OverAllLeaderBoardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderBoardInterface extends CrudRepository<OverAllLeaderBoardEntity,String> {
    List<OverAllLeaderBoardEntity> findAll();
    OverAllLeaderBoardEntity getByUserId(String userId);
}
