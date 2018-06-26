package com.constest.ContestAPI.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = ContestQuestionEntity.TABLE_NAME)
public class ContestQuestionEntity {

    static final String ID_COLUMN = "contest_question_id";
    static final String TABLE_NAME = "contest_question";
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name =  ContestQuestionEntity.ID_COLUMN)
    private String contestQuestionId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "contest_id",nullable = false)
    private ContestEntity contestEntity;

    private String questionId;
    private Integer points;
    private Integer visibleTime;
    private Integer sequence;
    private Boolean visible;

    @Override
    public String toString() {
        return "ContestQuestionEntity{" +
                "contestQuestionId='" + contestQuestionId + '\'' +
                ", contestEntity=" + contestEntity +
                ", questionId='" + questionId + '\'' +
                ", points=" + points +
                ", visibleTime=" + visibleTime +
                ", sequence=" + sequence +
                ", isVisible=" + visible +
                '}';
    }

    public String getContestQuestionId() {
        return contestQuestionId;
    }

    public void setContestQuestionId(String contestQuestionId) {
        this.contestQuestionId = contestQuestionId;
    }

    public ContestEntity getContestEntity() {
        return contestEntity;
    }

    public void setContestEntity(ContestEntity contestEntity) {
        this.contestEntity = contestEntity;
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

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
