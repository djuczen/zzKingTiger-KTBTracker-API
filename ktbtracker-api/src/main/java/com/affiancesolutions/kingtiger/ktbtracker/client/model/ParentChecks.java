package com.affiancesolutions.kingtiger.ktbtracker.client.model;

import com.affiancesolutions.kingtiger.ktbtracker.client.model.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.client.model.Metadata;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;


public class ParentChecks implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = -5981035371377246470L;

    /**
     * The auto-generated ID for this entity.
     */
    private Long id;

    /**
     * Object Relationship Mapping (ORM) many-to-one relationship to the candidate (Candidate) being checked.
     */
    private Candidate candidate;

    /**
     * The week (of the cycle) that is being checked by the parent or guardian.
     */
    private int week;

    /**
     * Flag indicating whether the candidate completed online journals for the week.
     */
    private boolean onlineJournals;

    /**
     * Flag indicating whether the candidate completed their requirements book for the week.
     */
    private boolean requirementsBook;

    /**
     * Flag indicating whether the candidate has made progress on their essay(s).
     */
    private boolean essayProgress;

    /**
     * Flag indicating whether the candidate has attended required classes for the week.
     */
    private boolean classAttendance;

    /**
     * The timestamp of when the parent or guardian signed-off on the candidate checks for the week.
     */
    private LocalDateTime checked;

    /**
     * The parent or guardian that signed-off on the candidate checks for the week.
     */
    private String checkedBy;

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

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public boolean isOnlineJournals() {
        return onlineJournals;
    }

    public void setOnlineJournals(boolean onlineJournals) {
        this.onlineJournals = onlineJournals;
    }

    public boolean isRequirementsBook() {
        return requirementsBook;
    }

    public void setRequirementsBook(boolean requirementsBook) {
        this.requirementsBook = requirementsBook;
    }

    public boolean isEssayProgress() {
        return essayProgress;
    }

    public void setEssayProgress(boolean essayProgress) {
        this.essayProgress = essayProgress;
    }

    public boolean isClassAttendance() {
        return classAttendance;
    }

    public void setClassAttendance(boolean classAttendance) {
        this.classAttendance = classAttendance;
    }

    public LocalDateTime getChecked() {
        return checked;
    }

    public void setChecked(LocalDateTime checked) {
        this.checked = checked;
    }

    public String getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetaData(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return String.format("ParentCheck(checked=%s, checkedBy=%s)", checked, checkedBy);
    }
}
