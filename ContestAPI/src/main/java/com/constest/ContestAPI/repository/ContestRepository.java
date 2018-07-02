package com.constest.ContestAPI.repository;

import com.constest.ContestAPI.entity.ContestEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestRepository extends CrudRepository<ContestEntity,String> {

    List<ContestEntity> findByCategoryId(String categoryId);
    List<ContestEntity> findByContestType(String contestType);
    List<ContestEntity> findAllByAdminId(String adminId);
    List<ContestEntity> findAllByCategoryIdAndContestType(String categoryId,String contestType);
}
