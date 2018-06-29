package com.constest.ContestAPI.controller;
import com.constest.ContestAPI.dto.QuestionDTO;
import com.constest.ContestAPI.dto.ReportDTO;
import com.constest.ContestAPI.dto.UserPointsDTO;
import com.constest.ContestAPI.entity.LeaderBoard;
import com.constest.ContestAPI.entity.OverAllLeaderBoard;
import com.constest.ContestAPI.service.UserPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/userPoints/")
public class UserPointsController {

    @Autowired
    UserPointsService userPointsServiceInterface;

    @RequestMapping(method = RequestMethod.POST,value = "/contest/subscribe")
    public boolean subscribe(@RequestBody UserPointsDTO userPointsDTO)
    {
        return userPointsServiceInterface.save(userPointsDTO);
    }


    @RequestMapping(method = RequestMethod.GET,value = "/getAll")
    public List<UserPointsDTO> getAll()
    {
        return userPointsServiceInterface.getAllContests();
    }


    @RequestMapping(method = RequestMethod.GET,value = "/contest/getOverAllLeaderBoard")
    public List<LeaderBoard> overAllLeadership()
    {
        return userPointsServiceInterface.getOverAllLeaderBoard();
    }


    @RequestMapping(method = RequestMethod.GET,value = "/contest/getContestWiseLeaderBoard/{contestId}")
    public List<LeaderBoard> getContestWiseLeaderBoard(@PathVariable("contestId") String contestId)
    {
        return userPointsServiceInterface.getContestWiseLeaderBoard(contestId);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/getReport/contestWise")
    public List<ReportDTO> getReport(@RequestBody List<String> contestIdList)
    {
        List<ReportDTO> reportDTOList = new ArrayList<>();
        for (String contestId:contestIdList) {
            Integer contestWiseUsers = userPointsServiceInterface.getByContestId(contestId);
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setContestId(contestId);
            reportDTO.setNumberOfUsers(contestWiseUsers);
            reportDTOList.add(reportDTO);
            System.out.println(reportDTO);
        }
        System.out.println(reportDTOList.size());
        return reportDTOList;

    }

    @RequestMapping(method = RequestMethod.GET,value = "/getReport/overAllUsers")
    public Integer getUsers()
    {
        Integer numberOfUsers = getNumberOfUsers();
        return numberOfUsers;
    }

    public Integer getNumberOfUsers() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String URL = "http://10.177.2.201:8082/question/getOne/";
        HttpEntity<Object> entity = new HttpEntity<Object>(httpHeaders);
        ResponseEntity<Integer> rs = restTemplate.exchange(URL, HttpMethod.GET,
                entity, new ParameterizedTypeReference<Integer>() {
                });
        if (rs.getStatusCode() == HttpStatus.OK) {
            System.out.println(restTemplate.getUriTemplateHandler().toString());
            return rs.getBody();
        }
        return null;
    }
}



