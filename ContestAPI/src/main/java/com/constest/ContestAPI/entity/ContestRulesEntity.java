package com.constest.ContestAPI.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = ContestRulesEntity.TABLE_NAME)
public class ContestRulesEntity {

    static final String TABLE_NAME = "contest_rules";
    static final String ID_COLUMN = "contest_id";

    @Id
    @Column(name =  ContestQuestionEntity.ID_COLUMN)
    String rulesId;

    int numQuestions;
    int numEasyQ;
    int numMediumQ;
    int numHardQ;
    int numTextQ;
    int numImageQ;
    int numAudioQ;
    int numVideoQ;

    public String getRulesId() {
        return rulesId;
    }

    public void setRulesId(String rulesId) {
        this.rulesId = "GlobalRule";
    }

    public static String getTableName() {
        return TABLE_NAME;
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

    @Override
    public String toString() {
        return "ContestRulesEntity{" +
                "numQuestions=" + numQuestions +
                ", numEasyQ=" + numEasyQ +
                ", numMediumQ=" + numMediumQ +
                ", numHardQ=" + numHardQ +
                ", numTextQ=" + numTextQ +
                ", numImageQ=" + numImageQ +
                ", numAudioQ=" + numAudioQ +
                ", numVideoQ=" + numVideoQ +
                '}';
    }
}
