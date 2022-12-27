package com.affiancesolutions.kingtiger.ktbtracker.server.model.entity;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Metadata;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "PARENT_CHECKS", indexes = {
        @Index(name = "PARENT_WEEK_CHECKS_NDX", columnList = "CANDIDATE_ID, WEEK", unique = true)
})
public class ParentChecks implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = -5981035371377246470L;

    /**
     * The auto-generated ID for this entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    /**
     * Object Relationship Mapping (ORM) many-to-one relationship to the candidate (Candidate) being checked.
     */
    @ManyToOne
    @JoinColumn(name = "CANDIDATE_ID", nullable = false)
    private Candidate candidate;

    /**
     * The week (of the cycle) that is being checked by the parent or guardian.
     */
    @Column(name = "WEEK", nullable = false)
    private int week;

    /**
     * Flag indicating whether the candidate completed online journals for the week.
     */
    @Column(name = "ONLINE_JOURNALS", nullable = false)
    private boolean onlineJournals;

    /**
     * Flag indicating whether the candidate completed their requirements book for the week.
     */
    @Column(name = "REQUIREMENTS_BOOK", nullable = false)
    private boolean requirementsBook;

    /**
     * Flag indicating whether the candidate has made progress on their essay(s).
     */
    @Column(name = "ESSAY_PROGRESS", nullable = false)
    private boolean essayProgress;

    /**
     * Flag indicating whether the candidate has attended required classes for the week.
     */
    @Column(name = "CLASS_ATTENDANCE", nullable = false)
    private boolean classAttendance;

    /**
     * The timestamp of when the parent or guardian signed-off on the candidate checks for the week.
     */
    @Column(name = "CHECKED", nullable = false)
    private LocalDateTime checked;

    /**
     * The parent or guardian that signed-off on the candidate checks for the week.
     */
    @Column(name = "CHECKED_BY", nullable = false)
    private String checkedBy;

    /**
     * Metadata (created, last modified) for the tracking record (Embedded)
     */
    @Embedded
    private Metadata metadata = new Metadata();

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
