package com.constest.ContestAPI.repository;

import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.LeaderBoard;
import com.constest.ContestAPI.entity.UserPointsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPointsRepository extends CrudRepository<UserPointsEntity,String> {

    List<UserPointsEntity> findAllByUserId(String userId);
    List<UserPointsEntity> findAllByContestEntity(ContestEntity contestEntity);
    List<UserPointsEntity> getByContestId(String contestId);

    @Query(value = "SELECT * FROM user_points p where p.contest_id = ?#{[0]} order by final_points desc",nativeQuery = true)
    List<UserPointsEntity> test(String contest_id);


//    @Query("SELECT new com.constest.ContestAPI.entity.LeaderBoard(v.userId,v.finalPoints) FROM UserPointsEntity v")
//    List<LeaderBoard> findSurveyCount();

    @Query(value ="SELECT v.user_id,v.final_points,dense_rank() over(order by v.final_points desc) FROM user_points v where v.contest_id = ?#{[0]}",nativeQuery = true)
    List<Object[]> getContestWiseLeaderBoard(String contest_id);

    @Query(value = "SELECT v.user_id,sum(v.final_points)as over_all_points,dense_rank() over (order by sum(v.final_points) desc) FROM user_points v group by v.user_id",nativeQuery = true)
    List<Object[]> overAllBoard();

}
