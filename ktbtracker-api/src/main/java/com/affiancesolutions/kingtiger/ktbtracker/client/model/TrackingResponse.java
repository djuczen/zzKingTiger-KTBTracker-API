package com.affiancesolutions.kingtiger.ktbtracker.client.model;

import com.affiancesolutions.kingtiger.ktbtracker.client.model.Cycle;
import com.affiancesolutions.kingtiger.ktbtracker.client.model.Metadata;
import com.affiancesolutions.kingtiger.ktbtracker.client.model.Requirements;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;


public class TrackingResponse implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = 5900044619481012717L;

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

    /**
     * Metadata (created, last modified) for the tracking record (Embedded)
     */
    private Metadata metadata = new Metadata();

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

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
