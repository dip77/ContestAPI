package com.constest.ContestAPI.dto;

public class ContestRulesDTO {

    String rulesId;

    int numQuestions;
    int numEasyQ;
    int numMediumQ;
    int numHardQ;
    int numTextQ;
    int numImageQ;
    int numAudioQ;
    int numVideoQ;

    @Override
    public String toString() {
        return "ContestRulesDTO{" +
                "rulesId='" + rulesId + '\'' +
                ", numQuestions=" + numQuestions +
                ", numEasyQ=" + numEasyQ +
                ", numMediumQ=" + numMediumQ +
                ", numHardQ=" + numHardQ +
                ", numTextQ=" + numTextQ +
                ", numImageQ=" + numImageQ +
                ", numAudioQ=" + numAudioQ +
                ", numVideoQ=" + numVideoQ +
                '}';
    }

    public String getRulesId() {
        return rulesId;
    }

    public void setRulesId(String rulesId) {
        this.rulesId = rulesId;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public int getNumEasyQ() {
        return numEasyQ;
    }

    public void setNumEasyQ(int numEasyQ) {
        this.numEasyQ = numEasyQ;
    }

    public int getNumMediumQ() {
        return numMediumQ;
    }

    public void setNumMediumQ(int numMediumQ) {
        this.numMediumQ = numMediumQ;
    }

    public int getNumHardQ() {
        return numHardQ;
    }

    public void setNumHardQ(int numHardQ) {
        this.numHardQ = numHardQ;
    }

    public int getNumTextQ() {
        return numTextQ;
    }

    public void setNumTextQ(int numTextQ) {
        this.numTextQ = numTextQ;
    }

    public int getNumImageQ() {
        return numImageQ;
    }

    public void setNumImageQ(int numImageQ) {
        this.numImageQ = numImageQ;
    }

    public int getNumAudioQ() {
        return numAudioQ;
    }

    public void setNumAudioQ(int numAudioQ) {
        this.numAudioQ = numAudioQ;
    }

    public int getNumVideoQ() {
        return numVideoQ;
    }

    public void setNumVideoQ(int numVideoQ) {
        this.numVideoQ = numVideoQ;
    }
}
