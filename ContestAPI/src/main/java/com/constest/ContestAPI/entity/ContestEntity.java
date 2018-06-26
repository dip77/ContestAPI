package com.constest.ContestAPI.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = ContestEntity.TABLE_NAME)
public class ContestEntity {

     static final String ID_COLUMN = "contest_id";
     static final String TABLE_NAME = "contest";


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = ContestEntity.ID_COLUMN)
    private String contestId;
    @JsonManagedReference
    @OneToMany
    @JoinColumn(name = "contest_id")
    private List<ContestQuestionEntity> contestQuestionEntityList;

    private String contestName;
    private String contestType;
    private String startDate;
    private String endDate;
    private String categoryId;
    private String adminId;
    private Integer questionVisibilityDuration;
    private Integer numberOfQuestions;
    private Integer bonus;

    @Override
    public String toString() {
        return "ContestEntity{" +
                "contestId='" + contestId + '\'' +
                ", contestName='" + contestName + '\'' +
                ", contestType='" + contestType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", categoryId='" + categoryId + '\'' +
                ", adminId='" + adminId + '\'' +
                ", questionVisibilityDuration=" + questionVisibilityDuration +
                ", bonus=" + bonus +
                '}';
    }

    public List<ContestQuestionEntity> getContestQuestionEntityList() {
        return contestQuestionEntityList;
    }

    public void setContestQuestionEntityList(List<ContestQuestionEntity> contestQuestionEntityList) {
        this.contestQuestionEntityList = contestQuestionEntityList;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public static String getIdColumn() {
        return ID_COLUMN;
    }

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getContestType() {
        return contestType;
    }

    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public Integer getQuestionVisibilityDuration() {
        return questionVisibilityDuration;
    }

    public void setQuestionVisibilityDuration(Integer questionVisibilityDuration) {
        this.questionVisibilityDuration = questionVisibilityDuration;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }
}
