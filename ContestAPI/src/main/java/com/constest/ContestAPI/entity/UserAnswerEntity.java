package com.constest.ContestAPI.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private String userContestId;
    private String userId;
    private String answer;
    private int points;
    private Boolean skipped;
    private Timestamp timeOfAnswer;

    public String getUserAnswerId() {
        return userAnswerId;
    }

    public void setUserAnswerId(String userAnswerId) {
        this.userAnswerId = userAnswerId;
    }

    @ManyToOne
    @JoinColumn(name = "contest_question_id")
    ContestQuestionEntity contestQuestionEntity;

    //  @ManyToOne
    //  @JoinColumn(name="userContestId",nullable = false)
    //  UserPointsEntity userPointsEntity;


    public Boolean getSkipped() {
        return skipped;
    }

    public ContestQuestionEntity getContestQuestionEntity() {
        return contestQuestionEntity;
    }


    public void setContestQuestionEntity(ContestQuestionEntity contestQuestionEntity) {
        this.contestQuestionEntity = contestQuestionEntity;
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

    public void setPoints(int points) {
        this.points = points;
    }

    public Timestamp getTimeOfAnswer() {
        return timeOfAnswer;
    }

    public void setTimeOfAnswer(Timestamp timeOfAnswer) {
        this.timeOfAnswer = timeOfAnswer;
    }

    @Override
    public String toString() {
        return "UserAnswerEntity{" +
                "userAnswerId='" + userAnswerId + '\'' +
                ", userContestId='" + userContestId + '\'' +
                ", userId='" + userId + '\'' +
                ", answer='" + answer + '\'' +
                ", points=" + points +
                ", skipped=" + skipped +
                ", timeOfAnswer=" + timeOfAnswer +
                ", contestQuestionEntity=" + contestQuestionEntity +
                '}';
    }
}
