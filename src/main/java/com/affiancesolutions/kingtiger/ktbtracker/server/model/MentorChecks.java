package com.affiancesolutions.kingtiger.ktbtracker.server.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "MENTOR_CHECKS", indexes = {
        @Index(name = "MENTOR_WEEK_CHECKS_NDX", columnList = "CANDIDATE_ID, WEEK", unique = true)
})
public class MentorChecks implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = 5900044619481012717L;

    /**
     * The auto-generated ID for this entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    /**
     * Object Relationshipl Mapping (ORM) many-to-one relationship to the candidate (Candidate) being checked.
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
     * Flag indicating whether poomsae progression was discussed with candidate.
     */
    @Column(name = "POOMSAE", nullable = false)
    private boolean poomsae;

    /**
     * Notes from the mentor about poomsae progression.
     */
    @Column(name = "POOMSAE_NOTES")
    private String poomsaeNotes;

    /**
     * Flag indicating whether self-defense progression was discussed with candidate.
     */
    @Column(name = "SELF_DEFENSE", nullable = false)
    private boolean selfDefense;

    /**
     * Notes (optional) from the mentor about self-defense progression.
     */
    @Column(name = "SELF_DEFENSE_NOTES")
    private String selfDefenseNotes;

    /**
     * Flag indicating whether weapons progression was discussed with candidate.
     */
    @Column(name = "WEAPONS", nullable = false)
    private boolean weapons;

    /**
     * Notes (optional) from the mentor about weapons progression.
     */
    @Column(name = "WEAPONS_NOTES")
    private String weaponsNotes;

    /**
     * Flag indicating whether overall requirements progression was discussed with candidate.
     */
    @Column(name = "REQUIREMENTS", nullable = false)
    private boolean requirements;

    /**
     * Notes (optional) from the mentor about overall requirement progression.
     */
    @Column(name = "REQUIREMENTS_NOTES")
    private String requirementsNotes;

    /**
     * Notes from the mentor about the mentoring session for the week.
     */
    @Column(name = "PRACTICED", nullable = false)
    private String practiced;

    /**
     * The timestamp of when the mentor signed-off on the candidate checks for the week.
     */
    @Column(name = "CHECKED", nullable = false)
    private LocalDateTime checked;

    /**
     * The mentor that signed-off on the candidate checks for the week.
     */
    @Column(name = "CHECKED_BY", nullable = false)
    private String checkedBy;

    /**
     * Metadata (created, last modified) for the mentor checklist record (Embedded)
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

    public boolean isPoomsae() {
        return poomsae;
    }

    public void setPoomsae(boolean poomsae) {
        this.poomsae = poomsae;
    }

    public String getPoomsaeNotes() {
        return poomsaeNotes;
    }

    public void setPoomsaeNotes(String poomsaeNotes) {
        this.poomsaeNotes = poomsaeNotes;
    }

    public boolean isSelfDefense() {
        return selfDefense;
    }

    public void setSelfDefense(boolean selfDefense) {
        this.selfDefense = selfDefense;
    }

    public String getSelfDefenseNotes() {
        return selfDefenseNotes;
    }

    public void setSelfDefenseNotes(String selfDefenseNotes) {
        this.selfDefenseNotes = selfDefenseNotes;
    }

    public boolean isWeapons() {
        return weapons;
    }

    public void setWeapons(boolean weapons) {
        this.weapons = weapons;
    }

    public String getWeaponsNotes() {
        return weaponsNotes;
    }

    public void setWeaponsNotes(String weaponsNotes) {
        this.weaponsNotes = weaponsNotes;
    }

    public boolean isRequirements() {
        return requirements;
    }

    public void setRequirements(boolean requirements) {
        this.requirements = requirements;
    }

    public String getRequirementsNotes() {
        return requirementsNotes;
    }

    public void setRequirementsNotes(String requirementsNotes) {
        this.requirementsNotes = requirementsNotes;
    }

    public String getPracticed() {
        return practiced;
    }

    public void setPracticed(String practiced) {
        this.practiced = practiced;
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

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
