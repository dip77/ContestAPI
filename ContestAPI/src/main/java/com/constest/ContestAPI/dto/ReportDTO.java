package com.constest.ContestAPI.dto;

public class ReportDTO {
    private String contestId;
    private Integer numberOfUsers;

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public Integer getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(Integer numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "contestId='" + contestId + '\'' +
                ", numberOfUsers=" + numberOfUsers +
                '}';
    }
}
