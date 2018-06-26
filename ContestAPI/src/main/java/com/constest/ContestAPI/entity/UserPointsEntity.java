package com.constest.ContestAPI.entity;


import com.constest.ContestAPI.dto.OverAllLeaderBoardDTO;
import com.constest.ContestAPI.dto.UserPointsDTO;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Id;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;


@Entity
@Table(name = UserPointsEntity.TABLE_NAME)
public class UserPointsEntity {

    private static final String ID_COLUMN = "user_contest_id";
    static final String TABLE_NAME = "user_points";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = UserPointsEntity.ID_COLUMN)
    private String userContestId;
    private String userId;
    private int finalPoints ;
    private int easyCorrectlyAnswered ;
    private int mediumCorrectlyAnswered ;
    private int hardCorrectlyAnswered ;
    //todo : phani : field has to be camel case, should not start with capital
    private int Bonus ;
    private int rank;


    @OneToOne
    @JoinColumn(name = "contest_id",nullable = false)
    private ContestEntity contestEntity;


    @Override
    public String toString() {
        return "UserPointsEntity{" +
                "userContestId='" + userContestId + '\'' +
                ", userId='" + userId + '\'' +
                ", finalPoints=" + finalPoints +
                ", easyCorrectlyAnswered=" + easyCorrectlyAnswered +
                ", mediumCorrectlyAnswered=" + mediumCorrectlyAnswered +
                ", hardCorrectlyAnswered=" + hardCorrectlyAnswered +
                ", Bonus=" + Bonus +
                ", rank=" + rank +
                ", contestEntity=" + contestEntity +
                '}';
    }

    public String getUserContestId() {
        return userContestId;
    }

    public void setUserContestId(String userContestId) {
        this.userContestId = userContestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFinalPoints() {
        return finalPoints;
    }

    public void setFinalPoints(int finalPoints) {
        this.finalPoints = finalPoints;
    }

    public int getEasyCorrectlyAnswered() {
        return easyCorrectlyAnswered;
    }

    public void setEasyCorrectlyAnswered(int easyCorrectlyAnswered) {
        this.easyCorrectlyAnswered = easyCorrectlyAnswered;
    }

    public int getMediumCorrectlyAnswered() {
        return mediumCorrectlyAnswered;
    }

    public void setMediumCorrectlyAnswered(int mediumCorrectlyAnswered) {
        this.mediumCorrectlyAnswered = mediumCorrectlyAnswered;
    }

    public int getHardCorrectlyAnswered() {
        return hardCorrectlyAnswered;
    }

    public void setHardCorrectlyAnswered(int hardCorrectlyAnswered) {
        this.hardCorrectlyAnswered = hardCorrectlyAnswered;
    }

    public int getBonus() {
        return Bonus;
    }

    public void setBonus(int bonus) {
        Bonus = bonus;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public ContestEntity getContestEntity() {
        return contestEntity;
    }

    public void setContestEntity(ContestEntity contestEntity) {
        this.contestEntity = contestEntity;
    }
}


