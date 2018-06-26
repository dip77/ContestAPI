package com.constest.ContestAPI.dto;

import java.util.Comparator;
import java.util.List;

public class UserPointsDTO {
    private String userId;
    private ContestDTO contestDTO;
    private String userContestId;
    private int finalPoints ;
    private int easyCorrectlyAnswered ;
    private int mediumCorrectlyAnswered ;
    private int hardCorrectlyAnswered ;
    private int bonus ;
    private int rank;

    @Override
    public String toString() {
        return "UserPointsDTO{" +
                "userId='" + userId + '\'' +
                ", contestDTO=" + contestDTO +
                ", userContestId='" + userContestId + '\'' +
                ", finalPoints=" + finalPoints +
                ", easyCorrectlyAnswered=" + easyCorrectlyAnswered +
                ", mediumCorrectlyAnswered=" + mediumCorrectlyAnswered +
                ", hardCorrectlyAnswered=" + hardCorrectlyAnswered +
                ", bonus=" + bonus +
                ", rank=" + rank +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ContestDTO getContestDTO() {
        return contestDTO;
    }

    public void setContestDTO(ContestDTO contestDTO) {
        this.contestDTO = contestDTO;
    }

    public String getUserContestId() {
        return userContestId;
    }

    public void setUserContestId(String userContestId) {
        this.userContestId = userContestId;
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
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}

class SortbyFinalPoints implements Comparator<UserPointsDTO>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(UserPointsDTO a, UserPointsDTO b)
    {
        return a.getFinalPoints() - b.getFinalPoints();
    }
}