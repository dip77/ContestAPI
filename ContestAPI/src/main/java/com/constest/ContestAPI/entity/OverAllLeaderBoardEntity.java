package com.constest.ContestAPI.entity;

import com.constest.ContestAPI.dto.OverAllLeaderBoardDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Comparator;

@Entity
@Table(name = OverAllLeaderBoardEntity.TABLE_NAME)
public class OverAllLeaderBoardEntity {
    static final String TABLE_NAME = "leaderBoard";


    @Id
    private String userId;
    private int overAllPoints;
    private int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOverAllPoints() {
        return overAllPoints;
    }

    public void setOverAllPoints(int overAllPoints) {
        this.overAllPoints = overAllPoints;
    }

    @Override
    public String toString() {
        return "OverAllLeaderBoardDTO{" +
                "userId='" + userId + '\'' +
                ", overAllPoints='" + overAllPoints + '\'' +
                '}';
    }


}


