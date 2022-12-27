package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CandidateTrackingResult implements Serializable {

    private static final long serialVersionUID = 6166178929111804946L;

    private String userId;

    private int candidateId;

    private int cycleId;

    private LocalDate startDate;

    private LocalDate endDate;

    Statistics statistics = new Statistics();

    List<TrackingResult> daily = new ArrayList<>();


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public List<TrackingResult> getDaily() {
        return daily;
    }

    public void setDaily(List<TrackingResult> daily) {
        this.daily = daily;
    }
}
