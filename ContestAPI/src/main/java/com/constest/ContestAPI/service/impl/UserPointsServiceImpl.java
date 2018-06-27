package com.constest.ContestAPI.service.impl;
import com.constest.ContestAPI.dto.ContestDTO;
import com.constest.ContestAPI.dto.OverAllLeaderBoardDTO;
import com.constest.ContestAPI.dto.UserPointsDTO;
import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.OverAllLeaderBoardEntity;
import com.constest.ContestAPI.entity.UserPointsEntity;
import com.constest.ContestAPI.repository.LeaderBoardInterface;
import com.constest.ContestAPI.repository.UserPointsRepository;
import com.constest.ContestAPI.service.UserPointsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserPointsServiceImpl implements UserPointsService {

    @Autowired
    UserPointsRepository userPointsRepositoryInterface;

    @Autowired
    LeaderBoardInterface userHistoryInterface;

    @Override
    public boolean save(UserPointsDTO userPointsDTO) {
        UserPointsEntity userPointsEntity = new UserPointsEntity();
        BeanUtils.copyProperties(userPointsDTO, userPointsEntity);
        ContestEntity contestEntity = new ContestEntity();
        BeanUtils.copyProperties(userPointsDTO.getContestDTO(),contestEntity);
        userPointsEntity.setContestEntity(contestEntity);
        OverAllLeaderBoardEntity overAllLeaderBoardEntity = new OverAllLeaderBoardEntity();
        overAllLeaderBoardEntity.setUserId(userPointsDTO.getUserId());
        overAllLeaderBoardEntity.setOverAllPoints(0);
        overAllLeaderBoardEntity.setRank(0);
        try {
            userHistoryInterface.save(overAllLeaderBoardEntity);
            System.out.println(overAllLeaderBoardEntity);
            UserPointsEntity userPointsEntity1 = userPointsRepositoryInterface.save(userPointsEntity);
            return true;
        }
        catch (Exception exception)
        {
            System.out.println(exception.toString());
            return false;
        }
    }

    public int getByUserId(String userId) {
        int sumPoints = 0;
        List<UserPointsDTO> userPointsDTOList = new ArrayList<>();
        List<UserPointsEntity> userPointsEntityList = userPointsRepositoryInterface.findAllByUserId(userId);
        for(UserPointsEntity userPointsEntity:userPointsEntityList)
        {
            UserPointsDTO userPointsDTO = new UserPointsDTO();
            BeanUtils.copyProperties(userPointsEntity,userPointsDTO);
            userPointsDTOList.add(userPointsDTO);
            sumPoints = userPointsDTO.getFinalPoints()+sumPoints;
        }

       OverAllLeaderBoardEntity overAllLeaderBoardEntity = userHistoryInterface.getByUserId(userId);
       overAllLeaderBoardEntity.setOverAllPoints(sumPoints);
       overAllLeaderBoardEntity.setUserId(userId);
       return sumPoints;
    }


    @Override
    public List<UserPointsDTO> getByContestId(String contestId) {
        ContestEntity contestEntity=new ContestEntity();
        contestEntity.setContestId(contestId);
        List<UserPointsEntity> userPointsEntityList = userPointsRepositoryInterface.findAllByContestEntity(contestEntity);
        List<UserPointsDTO> userPointsDTOList = new ArrayList<>();
        for(UserPointsEntity userPointsEntity:userPointsEntityList)
        {
            System.out.println(userPointsEntity);
            UserPointsDTO userPointsDTO = new UserPointsDTO();
            BeanUtils.copyProperties(userPointsEntity,userPointsDTO);
            System.out.println(userPointsDTO);
            ContestDTO contestDTO = new ContestDTO();
            ContestEntity contestEntity1 = userPointsEntity.getContestEntity();
            BeanUtils.copyProperties(contestEntity1,contestDTO);
            userPointsDTO.setContestDTO(contestDTO);
            userPointsDTOList.add(userPointsDTO);
        }
        return userPointsDTOList;
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

    //todo : phani : don't compute the rank for each user and store in the db, instead use
    @Override
    public boolean updatePoints(UserPointsDTO userPointsDTO) {
        UserPointsEntity userPointsEntity = new UserPointsEntity();
        BeanUtils.copyProperties(userPointsDTO,userPointsEntity);
        userPointsRepositoryInterface.save(userPointsEntity);
        OverAllLeaderBoardDTO overAllLeaderBoardDTO = new OverAllLeaderBoardDTO();
        overAllLeaderBoardDTO.setUserId(userPointsDTO.getUserId());
        overAllLeaderBoardDTO.setOverAllPoints(getByUserId(userPointsDTO.getUserId()));
        List<OverAllLeaderBoardEntity> overAllLeaderBoardEntityList = userHistoryInterface.findAll();
        Collections.sort(overAllLeaderBoardEntityList,Collections.reverseOrder(new SortbyFinalPoints()));
        int index = 1;
        for(OverAllLeaderBoardEntity overAllLeaderBoardEntity:overAllLeaderBoardEntityList)
        {
         overAllLeaderBoardEntity.setRank(index);
         userHistoryInterface.save(overAllLeaderBoardEntity);
         index++;
        }

        return true;
    }

    //todo : phani : overall leader board also should be based on the query
    @Override
    public boolean saveToHistory(OverAllLeaderBoardEntity overAllLeaderBoardEntity) {
        userHistoryInterface.save(overAllLeaderBoardEntity);
        return true;
    }

    @Override
    public List<OverAllLeaderBoardDTO> getAll() {
        List<OverAllLeaderBoardEntity> overAllLeaderBoardEntityList =  userHistoryInterface.findAll();
        List<OverAllLeaderBoardDTO> overAllLeaderBoardDTOList = new ArrayList<>();
        for(OverAllLeaderBoardEntity overAllLeaderBoardEntity:overAllLeaderBoardEntityList)
        {
            OverAllLeaderBoardDTO overAllLeaderBoardDTO = new OverAllLeaderBoardDTO();
            BeanUtils.copyProperties(overAllLeaderBoardEntity,overAllLeaderBoardDTO);
            overAllLeaderBoardDTOList.add(overAllLeaderBoardDTO);
        }
        return overAllLeaderBoardDTOList;
    }
}
class SortbyFinalPoints implements Comparator<OverAllLeaderBoardEntity>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(OverAllLeaderBoardEntity a, OverAllLeaderBoardEntity b)
    {
        return a.getOverAllPoints() - b.getOverAllPoints();
    }
}

