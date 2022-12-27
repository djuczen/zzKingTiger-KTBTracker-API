package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Requirements;

import java.io.Serializable;
import java.time.LocalDate;

public class CycleRequest implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = 2398091363083983802L;

    /**
     * The auto-generated ID for this entity.
     */
    private int id;

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
     * The day of the week when a cycle week begins, 0 = Sunday, 6 = Saturday.
     */
    private Integer cycleWeekStart;

    /**
     * Requirements (physical, classes, other) for the cycle. These particular values indicate the "goal" to be
     * reached for each requirement, with a 0 value meaning it is not required.
     */
    private Requirements requirements = new Requirements();


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Integer getCycleWeekStart() {
        return cycleWeekStart;
    }

    public void setCycleWeekStart(Integer cycleWeekStart) {
        this.cycleWeekStart = cycleWeekStart;
    }

    public Requirements getRequirements() {
        return requirements;
    }

    public void setRequirements(Requirements requirements) {
        this.requirements = requirements;
    }
}
