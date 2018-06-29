package com.constest.ContestAPI.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = UserAnswerEntity.TABLE_NAME)
public class UserAnswerEntity {

    public static final String TABLE_NAME = "userAnswer";
    private static final String ID_COLUMN = "userAnswerId";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = UserAnswerEntity.ID_COLUMN)
    private String userAnswerId;
    private String userId;
    private String answer;
    private Integer points;
    private Boolean skipped;
    private String timeOfAnswer;

    private Boolean answered;
    public String getUserAnswerId() {
        return userAnswerId;
    }

    public void setUserAnswerId(String userAnswerId) {
        this.userAnswerId = userAnswerId;
    }

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "contest_question_id")
    ContestQuestionEntity contestQuestionEntity;



    public Boolean getSkipped() {
        return skipped;
    }

    public ContestQuestionEntity getContestQuestionEntity() {
        return contestQuestionEntity;
    }


    public void setContestQuestionEntity(ContestQuestionEntity contestQuestionEntity) {
        this.contestQuestionEntity = contestQuestionEntity;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getPoints() {
        return points;
    }

    public void setSkipped(Boolean skipped) {
        this.skipped = skipped;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeOfAnswer() {
        return timeOfAnswer;
    }

    public void setTimeOfAnswer(String timeOfAnswer) {
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
        return "UserAnswerEntity{" +
                "userAnswerId='" + userAnswerId + '\'' +
                ", userId='" + userId + '\'' +
                ", answer='" + answer + '\'' +
                ", points=" + points +
                ", skipped=" + skipped +
                ", timeOfAnswer=" + timeOfAnswer +
                ", answered=" + answered +
                ", contestQuestionEntity=" + contestQuestionEntity +
                '}';
    }
}
