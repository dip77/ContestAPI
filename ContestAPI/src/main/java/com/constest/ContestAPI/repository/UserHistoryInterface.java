package com.constest.ContestAPI.repository;

import com.constest.ContestAPI.entity.OverAllLeaderBoardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHistoryInterface extends CrudRepository<OverAllLeaderBoardEntity,String> {
    List<OverAllLeaderBoardEntity> findAll();
}
