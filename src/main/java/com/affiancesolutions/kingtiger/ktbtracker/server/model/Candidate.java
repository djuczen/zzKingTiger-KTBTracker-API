package com.affiancesolutions.kingtiger.ktbtracker.server.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "CANDIDATES", indexes = {
        @Index(name = "CANDIDATE_CYCLE_NDX", columnList = "ID, CYCLE_ID", unique = true)
})
public class Candidate implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = 3543434596393961181L;

    /**
     * The auto-generated ID for this entity.
     */
    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    /**
     * Object Relationship Mapping (ORM) many-to-one relationship to the cycle (Cycle).
     */
    @ManyToOne
    @JoinColumn(name = "CYCLE_ID", nullable = false)
    private Cycle cycle;

    /**
     * Metadata (created, last modified) for the mentor checklist record (Embedded)
     */
    @Embedded
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
