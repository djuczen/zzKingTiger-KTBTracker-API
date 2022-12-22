package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FullStatisticsResult implements Serializable {

    private static final long serialVersionUID = -6096805136962829993L;

    private int candidateId;

    private int cycleId;

    private Statistics cycle = new Statistics();

    private List<Statistics> weekly = new ArrayList<>();

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public int getCycleId() {
        return cycleId;
    }

    public void setCycleId(int cycleId) {
        this.cycleId = cycleId;
    }

    public Statistics getCycle() {
        return cycle;
    }

    public void setCycle(Statistics cycle) {
        this.cycle = cycle;
    }

    public List<Statistics> getWeekly() {
        return weekly;
    }

    public void setWeekly(List<Statistics> weekly) {
        this.weekly = weekly;
    }
}
