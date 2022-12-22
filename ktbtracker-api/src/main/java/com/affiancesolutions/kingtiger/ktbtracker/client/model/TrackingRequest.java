package com.affiancesolutions.kingtiger.ktbtracker.client.model;

import java.io.Serializable;
import java.time.LocalDate;


public class TrackingRequest implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = -3464500105176369527L;

    /**
     * The auto-generated ID for this entity.
     */
    private Long id;

    /**
     * The date the candidate is tracking (this along with the candidate form a unique index).
     */
    private LocalDate trackingDate;

    /**
     * The candidate that is being tracked (this along with the tracking date form a unique index).
     */
    private int candidateId;

    /**
     * The cycle that is being tracked.
     */
    private int cycleId;

    /**
     * The requirements being tracked.
     */
    private Requirements requirements = new Requirements();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
