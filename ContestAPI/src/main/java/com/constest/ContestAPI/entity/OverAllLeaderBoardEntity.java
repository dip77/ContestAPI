package com.constest.ContestAPI.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = OverAllLeaderBoardEntity.TABLE_NAME)
public class OverAllLeaderBoardEntity {
    static final String TABLE_NAME = "leaderBoard";
    static final String ID_COLUMN = "id";

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name =  OverAllLeaderBoardEntity.ID_COLUMN)
    private String id;
    private String userId;
    private int overAllPoints;
    private int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
