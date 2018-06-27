package com.constest.ContestAPI.dto;

import com.constest.ContestAPI.entity.ContestQuestionEntity;

import java.sql.Timestamp;

public class UserAnswerDTO {
    private String userAnswerId;
    private ContestQuestionDTO contestQuestionDTO;
    private String userId;
    private String answer;
    private Integer points;
    private Boolean skipped;
    private Timestamp timeOfAnswer;
    private  Boolean answered;



    public ContestQuestionDTO getContestQuestionDTO() {
        return contestQuestionDTO;
    }

    public void setContestQuestionDTO(ContestQuestionDTO contestQuestionDTO) {
        this.contestQuestionDTO = contestQuestionDTO;
    }
    public String getUserAnswerId() {
        return userAnswerId;
    }

    public void setUserAnswerId(String userAnswerId) {
        this.userAnswerId = userAnswerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Boolean getSkipped() {
        return skipped;
    }

    public void setSkipped(Boolean skipped) {
        this.skipped = skipped;
    }

    public Timestamp getTimeOfAnswer() {
        return timeOfAnswer;
    }

    public void setTimeOfAnswer(Timestamp timeOfAnswer) {
        this.timeOfAnswer = timeOfAnswer;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getAnswered() {
        return answered;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }

    @Override
    public String toString() {
        return "UserAnswerDTO{" +
                "userAnswerId='" + userAnswerId + '\'' +
                ", contestQuestionDTO=" + contestQuestionDTO +
                ", userId='" + userId + '\'' +
                ", answer='" + answer + '\'' +
                ", points=" + points +
                ", skipped=" + skipped +
                ", timeOfAnswer=" + timeOfAnswer +
                ", answered=" + answered +
                '}';
    }
}
