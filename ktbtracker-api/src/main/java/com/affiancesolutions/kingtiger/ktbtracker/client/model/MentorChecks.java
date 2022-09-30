package com.affiancesolutions.kingtiger.ktbtracker.client.model;

import com.affiancesolutions.kingtiger.ktbtracker.client.model.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.client.model.Metadata;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;


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
    private Long id;

    /**
     * Object Relationshipl Mapping (ORM) many-to-one relationship to the candidate (Candidate) being checked.
     */
    private Candidate candidate;

    /**
     * The week (of the cycle) that is being checked by the parent or guardian.
     */
    private int week;

    /**
     * Flag indicating whether poomsae progression was discussed with candidate.
     */
    private boolean poomsae;

    /**
     * Notes from the mentor about poomsae progression.
     */
    private String poomsaeNotes;

    /**
     * Flag indicating whether self-defense progression was discussed with candidate.
     */
    private boolean selfDefense;

    /**
     * Notes (optional) from the mentor about self-defense progression.
     */
    private String selfDefenseNotes;

    /**
     * Flag indicating whether weapons progression was discussed with candidate.
     */
    private boolean weapons;

    /**
     * Notes (optional) from the mentor about weapons progression.
     */
    private String weaponsNotes;

    /**
     * Flag indicating whether overall requirements progression was discussed with candidate.
     */
    private boolean requirements;

    /**
     * Notes (optional) from the mentor about overall requirement progression.
     */
    private String requirementsNotes;

    /**
     * Notes from the mentor about the mentoring session for the week.
     */
    private String practiced;

    /**
     * The timestamp of when the mentor signed-off on the candidate checks for the week.
     */
    private LocalDateTime checked;

    /**
     * The mentor that signed-off on the candidate checks for the week.
     */
    private String checkedBy;

    /**
     * Metadata (created, last modified) for the mentor checklist record (Embedded)
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
