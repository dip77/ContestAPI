package com.constest.ContestAPI.entity;

import java.math.BigInteger;

public class OverAllLeaderBoard {
    private String userId;
    private String overAllPoints;
    private BigInteger rank;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOverAllPoints() {
        return overAllPoints;
    }

    public void setOverAllPoints(String overAllPoints) {
        this.overAllPoints = overAllPoints;
    }

    public BigInteger getRank() {
        return rank;
    }

    public void setRank(BigInteger rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "OverAllLeaderBoard{" +
                "userId='" + userId + '\'' +
                ", overAllPoints='" + overAllPoints + '\'' +
                ", rank=" + rank +
                '}';
    }
}
