package com.constest.ContestAPI.service.impl;
import com.constest.ContestAPI.dto.ContestDTO;
import com.constest.ContestAPI.dto.UserPointsDTO;
import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.LeaderBoard;
import com.constest.ContestAPI.entity.OverAllLeaderBoard;
import com.constest.ContestAPI.entity.UserPointsEntity;
import com.constest.ContestAPI.repository.UserPointsRepository;
import com.constest.ContestAPI.service.UserPointsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class UserPointsServiceImpl implements UserPointsService {

    @Autowired
    UserPointsRepository userPointsRepositoryInterface;

    @Override
    public boolean save(UserPointsDTO userPointsDTO) {
        UserPointsEntity userPointsEntity = new UserPointsEntity();
        BeanUtils.copyProperties(userPointsDTO, userPointsEntity);
        ContestEntity contestEntity = new ContestEntity();
        BeanUtils.copyProperties(userPointsDTO.getContestDTO(),contestEntity);
        userPointsEntity.setContestEntity(contestEntity);
        UserPointsEntity userPointsEntity1 = userPointsRepositoryInterface.save(userPointsEntity);
        return true;
    }

    @Override
    public List<UserPointsDTO> getAllContests() {
        List<UserPointsEntity> userPointsEntityList = (List<UserPointsEntity>) userPointsRepositoryInterface.findAll();
        List<UserPointsDTO> userPointsDTOList = new ArrayList<>();
        System.out.println(userPointsDTOList);
        for(UserPointsEntity userPointsEntity :userPointsEntityList)
        {
            UserPointsDTO userPointsDTO = new UserPointsDTO();
            ContestDTO contestDTO = new ContestDTO();
            BeanUtils.copyProperties(userPointsEntity,userPointsDTO);
            ContestEntity contestEntity = userPointsEntity.getContestEntity();
            BeanUtils.copyProperties(contestEntity,contestDTO);
            userPointsDTO.setContestDTO(contestDTO);
            userPointsDTOList.add(userPointsDTO);
        }
        return userPointsDTOList;
    }

    @Override
    public int getByContestId(String contestId) {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setContestId(contestId);
        List<UserPointsEntity> userPointsEntityList= userPointsRepositoryInterface.getByContestEntity(contestEntity);
        return userPointsEntityList.size();
    }


    @Override
    public List<LeaderBoard> getOverAllLeaderBoard() {
        List<Object[]> objects = userPointsRepositoryInterface.overAllBoard();
        List<LeaderBoard> leaderBoards = new ArrayList<>();
        int index = 0;
        for(Object objects1:objects)
        {
            Object[] objects2 = objects.get(index);
            LeaderBoard leaderBoard = new LeaderBoard();
            String userId = (String) objects2[0];
           leaderBoard.setUserId(getUserName(userId));


//      leaderBoard.setFinalPoints((BigInteger)objects2[1]);
            if(objects2[1] instanceof  String)
                System.out.println("string");
            else if(objects2[1] instanceof BigInteger)
                System.out.println("Big");
            else
                System.out.println("none");
            leaderBoard.setFinalPoints((BigInteger)objects2[1]);
          //  leaderBoard.setPointInt((Integer)objects2[1]);
            leaderBoard.setFinalPointsString(((BigInteger)objects2[1]).toString());
            System.out.println(leaderBoard.getFinalPoints());
            leaderBoard.setRank((BigInteger)objects2[2]);
            leaderBoards.add(leaderBoard);
            System.out.println(leaderBoard);
            index++;
        }
        return leaderBoards;
    }

    @Override
    public List<LeaderBoard> getContestWiseLeaderBoard(String contestId) {
       List<Object[]> objects = userPointsRepositoryInterface.getContestWiseLeaderBoard(contestId);
       List<LeaderBoard> leaderBoards = new ArrayList<>();
       int index = 0;
       for(Object objects1:objects)
       {
           Object[] objects2 = objects.get(index);
           LeaderBoard leaderBoard = new LeaderBoard();
           String userId = (String) objects2[0];
           leaderBoard.setUserId(getUserName(userId));
           leaderBoard.setFinalPoints(BigInteger.valueOf((int)objects2[1]));
           leaderBoard.setRank((BigInteger)objects2[2]);
           leaderBoards.add(leaderBoard);
           index++;
       }
       return leaderBoards;
    }

    public String getUserName(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String URL = "http://10.177.2.201:8082/user/getUserName/"+userId;
        HttpEntity<Object> entity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<String> rs = restTemplate.exchange(URL, HttpMethod.GET,
                entity, new ParameterizedTypeReference<String>() {
                });
        if (rs.getStatusCode() == HttpStatus.OK) {
            System.out.println(restTemplate.getUriTemplateHandler().toString());
            return rs.getBody();
        }
        return null;
    }
}


