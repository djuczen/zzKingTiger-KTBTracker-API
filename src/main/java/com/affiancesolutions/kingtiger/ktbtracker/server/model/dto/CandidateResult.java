package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.server.utils.PersonNameComponents;

import java.io.Serializable;

public class CandidateResult implements Serializable {

    private static final long serialVersionUID = -7310047374763076607L;

    private int id;

    private int cycleId;

    private String userId;

    private String displayName;

    public CandidateResult() {}

    public CandidateResult(Candidate candidate) {
        this.id = candidate.getId();
        this.cycleId = candidate.getCycle().getId();
        this.userId = candidate.getUser().getId();
        this.displayName = candidate.getUser().getDisplayName();

        new PersonNameComponents(this.displayName);
        new PersonNameComponents("Mr. David J. Uczen, Ret.");
    }

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFamilyNameOrder() {
        return new PersonNameComponents(this.displayName).familyNameSequence();
    }
}
