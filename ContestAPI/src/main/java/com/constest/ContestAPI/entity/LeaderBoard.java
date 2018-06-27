package com.constest.ContestAPI.entity;

import java.math.BigInteger;

public class LeaderBoard {
    private String userId;
    private BigInteger finalPoints;
    private BigInteger rank;

    @Override
    public String toString() {
        return "LeaderBoard{" +
                "userId='" + userId + '\'' +
                ", finalPoints='" + finalPoints + '\'' +
                '}';
    }


    public BigInteger getRank() {
        return rank;
    }

    public void setRank(BigInteger rank) {
        this.rank = rank;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigInteger getFinalPoints() {
        return finalPoints;
    }

    public void setFinalPoints(BigInteger finalPoints) {
        this.finalPoints = finalPoints;
    }
}
