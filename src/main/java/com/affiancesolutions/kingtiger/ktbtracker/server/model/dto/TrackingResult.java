package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Metadata;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Requirements;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Tracking;

import java.io.Serializable;
import java.time.LocalDate;

public class TrackingResult implements Serializable {

    private static final long serialVersionUID = -4664674705364850108L;

    private int id;

    private LocalDate trackingDate;

    private int candidateId;

    private int cycleId;

    private Requirements requirements = new Requirements();

    private Metadata metadata = new Metadata();

    public TrackingResult() {}

    public TrackingResult(Candidate candidate, LocalDate trackingDate) {
        this.trackingDate = trackingDate;
        this.candidateId = candidate.getId();
        this.cycleId = candidate.getCycle().getId();
    }

    public TrackingResult(Tracking tracking) {
        this.id = tracking.getId();
        this.trackingDate = tracking.getTrackingDate();
        this.candidateId = tracking.getCandidate().getId();
        this.cycleId = tracking.getCandidate().getCycle().getId();
        this.requirements = tracking.getRequirements();
        this.metadata = tracking.getMetadata();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getTrackingDate() {
        return trackingDate;
    }

    public void setTrackingDate(LocalDate trackingDate) {
        this.trackingDate = trackingDate;
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

    public Requirements getRequirements() {
        return requirements;
    }

    public void setRequirements(Requirements requirements) {
        this.requirements = requirements;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return String.format("TrackingResult(id=%d, candidateId=%s, trackingDate=%s)",
                getId(), getCandidateId(), getTrackingDate());
    }
}
