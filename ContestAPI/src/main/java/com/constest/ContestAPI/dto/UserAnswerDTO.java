package com.constest.ContestAPI.dto;

import com.constest.ContestAPI.entity.ContestQuestionEntity;

import java.sql.Timestamp;

public class UserAnswerDTO {
    private String userAnswerId;
    private String contestQuestionId;
    private ContestQuestionDTO contestQuestionDTO;
    private String userContestId;
    private String userId;
    private String answer;
    private int points;
    private Boolean skipped;
    private Timestamp timeOfAnswer;


    @Override
    public String toString() {
        return "UserAnswerDTO{" +
                "userAnswerId='" + userAnswerId + '\'' +
                ", contestQuestionId='" + contestQuestionId + '\'' +
                ", contestQuestionDTO=" + contestQuestionDTO +
                ", userContestId='" + userContestId + '\'' +
                ", userId='" + userId + '\'' +
                ", answer='" + answer + '\'' +
                ", points=" + points +
                ", skipped=" + skipped +
                ", timeOfAnswer=" + timeOfAnswer +
                '}';
    }

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

    public String getContestQuestionId() {
        return contestQuestionId;
    }

    public void setContestQuestionId(String contestQuestionId) {
        this.contestQuestionId = contestQuestionId;
    }

    public String getUserContestId() {
        return userContestId;
    }

    public void setUserContestId(String userContestId) {
        this.userContestId = userContestId;
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
        skipped = skipped;
    }

    public Timestamp getTimeOfAnswer() {
        return timeOfAnswer;
    }

    public void setTimeOfAnswer(Timestamp timeOfAnswer) {
        this.timeOfAnswer = timeOfAnswer;
    }

}
