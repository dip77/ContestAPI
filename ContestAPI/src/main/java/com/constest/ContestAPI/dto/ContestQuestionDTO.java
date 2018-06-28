package com.constest.ContestAPI.dto;

import com.constest.ContestAPI.entity.ContestEntity;

import java.util.List;

public class  ContestQuestionDTO {
    private ContestDTO contestDTO;
    private String questionId;
    private Integer points;
    private Integer visibleTime;
    private Integer sequence;
    private Boolean visible;
    private String contestQuestionId;
    private QuestionDTO questionDTO;
    private UserAnswerDTO userAnswerDTO;
    private List<UserAnswerDTO> allUserAnswerEntityList;

    public List<UserAnswerDTO> getAllUserAnswerEntityList() {
        return allUserAnswerEntityList;
    }

    public void setAllUserAnswerEntityList(List<UserAnswerDTO> allUserAnswerEntityList) {
        this.allUserAnswerEntityList = allUserAnswerEntityList;
    }

    public UserAnswerDTO getUserAnswerDTO() {
        return userAnswerDTO;
    }

    public void setUserAnswerDTO(UserAnswerDTO userAnswerDTO) {
        this.userAnswerDTO = userAnswerDTO;
    }

    public QuestionDTO getQuestionDTO() {
        return questionDTO;
    }

    public void setQuestionDTO(QuestionDTO questionDTO) {
        this.questionDTO = questionDTO;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "ContestQuestionDTO{" +
                "contestEntity=" + contestDTO +
                ", questionId='" + questionId + '\'' +
                ", points=" + points +
                ", visibleTime=" + visibleTime +
                ", sequence=" + sequence +
                ", visible=" + visible +
                ", contestQuestionId='" + contestQuestionId + '\'' +
                '}';
    }

    public ContestDTO getContestDTO() {
        return contestDTO;
    }

    public void setContestDTO(ContestDTO contestDTO) {
        this.contestDTO = contestDTO;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getVisibleTime() {
        return visibleTime;
    }

    public void setVisibleTime(Integer visibleTime) {
        this.visibleTime = visibleTime;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }


    public String getContestQuestionId() {
        return contestQuestionId;
    }

    public void setContestQuestionId(String contestQuestionId) {
        this.contestQuestionId = contestQuestionId;
    }
}
