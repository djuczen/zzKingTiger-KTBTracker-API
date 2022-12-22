package com.affiancesolutions.kingtiger.ktbtracker.client.model;

import com.affiancesolutions.kingtiger.ktbtracker.client.model.Metadata;
import com.affiancesolutions.kingtiger.ktbtracker.client.model.Requirements;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Cycle implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = 2398091363083983802L;

    /**
     * The auto-generated ID for this entity.
     */
    private Long id;

    /**
     * Title assigned to the cycle, typically {Spring|Fall} 20XX.
     */
    private String title;

    private String alias;

    /**
     * The offical start date of the cycle.
     */
    private LocalDate cycleStart;

    /**
     * The official end date of the cycle.
     */
    private LocalDate cycleEnd;

    /**
     * A pre-start date that candidates can start to track requirements early.
     */
    private LocalDate cyclePreStart;

    /**
     * A post-end date that candidate are provided extra time to complete requirements.
     */
    private LocalDate cyclePostEnd;

    /**
     * Requirements (physical, classes, other) for the cycle. These particular values indicate the "goal" to be
     * reached for each requirement, with a 0 value meaning it is not required.
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

    public String getTitle() {
        return title;
    }

    public String getAlias() {
        return alias ;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getCycleStart() {
        return cycleStart;
    }

    public void setCycleStart(LocalDate cycleStart) {
        this.cycleStart = cycleStart;
    }

    public LocalDate getCycleEnd() {
        return cycleEnd;
    }

    public void setCycleEnd(LocalDate cycleEnd) {
        this.cycleEnd = cycleEnd;
    }

    public LocalDate getCyclePreStart() {
        return cyclePreStart;
    }

    public void setCyclePreStart(LocalDate cyclePreStart) {
        this.cyclePreStart = cyclePreStart;
    }

    public LocalDate getCyclePostEnd() {
        return cyclePostEnd;
    }

    public void setCyclePostEnd(LocalDate cyclePostEnd) {
        this.cyclePostEnd = cyclePostEnd;
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

    public void setMetaData(Metadata metadata) {
        this.metadata = metadata;
    }

    public int getCycleDays() {
        return (int)(ChronoUnit.DAYS.between(cycleStart, cycleEnd));
    }

    public int getCycleDay() {
        return (int)(ChronoUnit.DAYS.between(cycleStart, LocalDate.now()));
    }

    public String generateAlias(String title) {
        String generated = title != null ? title : "";
        generated = generated.toLowerCase().replaceAll("[()'" + "\"\\s_]", "-");

        return generated;
    }

    @Override
    public String toString() {
        return String.format("Cycle(id=%d, alias=%s, title='%s')", id, alias, title);
    }

}
