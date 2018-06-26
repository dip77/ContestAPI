package com.constest.ContestAPI.repository;

import com.constest.ContestAPI.entity.ContestQuestionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestQuestionRepository extends CrudRepository<ContestQuestionEntity,String> {

}
