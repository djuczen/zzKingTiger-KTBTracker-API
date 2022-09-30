package com.affiancesolutions.kingtiger.ktbtracker.client.model;

import com.affiancesolutions.kingtiger.ktbtracker.client.model.Cycle;
import com.affiancesolutions.kingtiger.ktbtracker.client.model.Metadata;
import jakarta.persistence.*;

import java.io.Serializable;

public class Candidate implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = 3543434596393961181L;

    /**
     * The auto-generated ID for this entity.
     */
    private String id;

    /**
     * Object Relationship Mapping (ORM) many-to-one relationship to the cycle (Cycle).
     */
    private Cycle cycle;

    /**
     * Metadata (created, last modified) for the mentor checklist record (Embedded)
     */
    private Metadata metadata = new Metadata();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return String.format("Candidate(id=%s, cycle=%s)", id, cycle);
    }
}
