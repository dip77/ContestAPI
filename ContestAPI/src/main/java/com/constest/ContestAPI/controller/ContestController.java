package com.constest.ContestAPI.controller;

import com.constest.ContestAPI.dto.ContestDTO;
import com.constest.ContestAPI.dto.ContestQuestionDTO;
import com.constest.ContestAPI.dto.QuestionDTO;
import com.constest.ContestAPI.entity.ContestEntity;
import com.constest.ContestAPI.entity.ContestQuestionEntity;
import com.constest.ContestAPI.service.impl.ContestServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contest")
public class ContestController {

    @Autowired
    private ContestServiceImpl contestService;

    @RequestMapping(method = RequestMethod.POST, value = "/createContest")
    public Boolean saveContest(@RequestBody ContestDTO contestDTO) {
        ContestEntity contestEntity = new ContestEntity();
        BeanUtils.copyProperties(contestDTO, contestEntity);
        return contestService.saveContest(contestEntity);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    public List<ContestDTO> getAllContest() {
        List<ContestEntity> contestEntityList = contestService.getAll();
        List<ContestDTO> contestDTOList = new ArrayList<ContestDTO>();
        for (ContestEntity contestEntity : contestEntityList) {
            ContestDTO contestDTO = new ContestDTO();
          //  System.out.println(contestEntity.getContestQuestionEntityList());
            List<ContestQuestionDTO> contestQuestionDTOList=new ArrayList<ContestQuestionDTO>();
            BeanUtils.copyProperties(contestEntity, contestDTO);
            for(ContestQuestionEntity contestQuestionEntity:contestEntity.getContestQuestionEntityList())
            {
                        ContestQuestionDTO contestQuestionDTO=new ContestQuestionDTO();
                        BeanUtils.copyProperties(contestQuestionEntity,contestQuestionDTO);
                        contestQuestionDTOList.add(contestQuestionDTO);
            }
            contestDTO.setContestQuestionDTOList(contestQuestionDTOList);
            contestDTOList.add(contestDTO);
        }
        return contestDTOList;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/getContestsByCategory/{categoryId}")
    public List<ContestDTO> getContestsByCategory(@PathVariable("categoryId") String categoryId) {
        List<ContestEntity> contestEntityList = contestService.getAllByCategory(categoryId);
        List<ContestDTO> contestDTOList = new ArrayList<ContestDTO>();
        for (ContestEntity contestEntity : contestEntityList) {
            ContestDTO contestDTO = new ContestDTO();
            BeanUtils.copyProperties(contestEntity, contestDTO);
            contestDTOList.add(contestDTO);
        }
        return contestDTOList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getContestsByType/{contestType}")
    public List<ContestDTO> getContestsByType(@PathVariable("contestType") String contestType) {
        List<ContestEntity> contestEntityList = contestService.getAllByContestType(contestType);
        List<ContestDTO> contestDTOList = new ArrayList<ContestDTO>();
        for (ContestEntity contestEntity : contestEntityList) {
            ContestDTO contestDTO = new ContestDTO();
            BeanUtils.copyProperties(contestEntity, contestDTO);
            contestDTOList.add(contestDTO);
        }
        return contestDTOList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getContestQuestions/{contestId}")
    public ContestDTO getContestQuestions(@PathVariable("contestId") String contestId) {
        ContestEntity contestEntity = contestService.getAllContestQuestions(contestId);
        ContestDTO contestDTO = new ContestDTO();
        BeanUtils.copyProperties(contestEntity, contestDTO);
    List<ContestQuestionDTO> contestQuestionDTOList=new ArrayList<ContestQuestionDTO>();
        for(ContestQuestionEntity contestQuestionEntity:contestEntity.getContestQuestionEntityList())
        {
            ContestQuestionDTO contestQuestionDTO=new ContestQuestionDTO();
            BeanUtils.copyProperties(contestQuestionEntity,contestQuestionDTO);
            QuestionDTO questionDTO=new QuestionDTO();
            contestQuestionDTO.setQuestionDTO(questionDTO);
            questionDTO.setAnswer("A");
            questionDTO.setAnswerType("single");
            questionDTO.setDifficulty("easy");
            questionDTO.setOptionOne("option one");
            questionDTO.setOptionTwo("option two");
            questionDTO.setOptionThree("option three");
            questionDTO.setQuestionText("This is demo question?");
            questionDTO.setQuestionType("text");
            contestQuestionDTOList.add(contestQuestionDTO);
        }
        contestDTO.setContestQuestionDTOList(contestQuestionDTOList);
        return contestDTO;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/getContestByAdminId/{adminId}")
    public ContestDTO getContestsByTAdmin(@PathVariable("adminId") String adminId) {
        ContestEntity contestEntity = contestService.getContestByAdmin(adminId);
        ContestDTO contestDTO = new ContestDTO();
        if (contestEntity == null)
            return contestDTO;
        BeanUtils.copyProperties(contestEntity, contestDTO);
        return contestDTO;

    }

}
