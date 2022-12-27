package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import java.io.Serializable;

public class CandidateRequest implements Serializable {

    private static final long serialVersionUID = 4613007804966402978L;

    private int id;

    private int cycleId;

    private String userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCycleId() {
        return cycleId;
    }

    public void setCycleId(int cycleId) {
        this.cycleId = cycleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
