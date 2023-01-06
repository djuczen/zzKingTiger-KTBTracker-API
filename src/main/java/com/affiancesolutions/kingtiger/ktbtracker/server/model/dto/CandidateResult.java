package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Metadata;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.PhysicalExam;
import com.affiancesolutions.kingtiger.ktbtracker.server.utils.PersonNameComponents;

import java.io.Serializable;

public class CandidateResult implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = 3543434596393961181L;

    private PersonNameComponents personNameComponents;

    /**
     * The auto-generated ID for this entity.
     */
    private int id;

    /**
     * Object Relationship Mapping (ORM) many-to-one relationship to the USERS table (User).
     */
    private String userId;

    private String displayName;

    private String email;

    /**
     * Object Relationship Mapping (ORM) many-to-one relationship to the cycle (Cycle).
     */
    private int cycleId;

    /**
     * Indicate if the candidate is auditing the cycle (no final testing).
     */
    private boolean audit;

    /**
     * Indicate if the candidate is continuing a previously incomplete or audited cycle. If non-zero, then
     * up to 1/3 of certain requirements will be carried over from the indicated previous cycle.
     */
    private int cycleCont;

    /**
     * Indicate if the candidate is a "Poom" candidate (under age 13). By default, candidates are treated as adults.
     * Some requirement targets may be reduced for Poom candidate.
     */
    private boolean poom;

    /**
     * Belt rank (current rank), where 0 is BoCho Dan, 1 is Il (First) Dan, etc. Masters are 4 (Fourth Dan) or higher,
     * and Grandmaster is 6 (Sixth Dan) or higher).
     */
    private int beltRank;

    /**
     * Completed required essays (varies depending on belt rank)
     */
    private int essays;

    /**
     * Completed required recommendation letters (varies depending on belt rank).
     */
    private int letters;

    /**
     * Score obtained on the written pre-exam.
     */
    private double preExamWritten;

    /**
     * Score obtained on the written exam.
     */
    private double examWritten;

    /**
     * Physical exam results (pre- and official physical exam results).
     */
    private PhysicalExam physicalExam = new PhysicalExam();

    /**
     * Metadata (created, last modified) for the mentor checklist record (Embedded)
     */
    private Metadata metadata = new Metadata();

    public CandidateResult() {};

    public CandidateResult(Candidate candidate) {
        this.id = candidate.getId();
        this.userId = candidate.getUser().getUserId();
        this.displayName = candidate.getUser().getDisplayName();
        this.email = candidate.getUser().getEmail();
        this.personNameComponents = new PersonNameComponents(candidate.getUser().getDisplayName());
        this.cycleId = candidate.getCycle().getId();
        this.cycleCont = candidate.getCycleCont();
        this.audit = candidate.isAudit();
        this.poom = candidate.isPoom();
        this.beltRank = candidate.getBeltRank();
        this.essays = candidate.getEssays();
        this.letters = candidate.getLetters();
        this.preExamWritten = candidate.getPreExamWritten();
        this.examWritten = candidate.getExamWritten();
        this.physicalExam = candidate.getPhysicalExam();
        this.metadata = candidate.getMetadata();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCycleId() {
        return cycleId;
    }

    public void setCycleId(int cycleId) {
        this.cycleId = cycleId;
    }

    public boolean isAudit() {
        return audit;
    }

    public void setAudit(boolean audit) {
        this.audit = audit;
    }

    public int getCycleCont() {
        return cycleCont;
    }

    public void setCycleCont(int cycleCont) {
        this.cycleCont = cycleCont;
    }

    public boolean isPoom() {
        return poom;
    }

    public void setPoom(boolean poom) {
        this.poom = poom;
    }

    public int getBeltRank() {
        return beltRank;
    }

    public void setBeltRank(int beltRank) {
        this.beltRank = beltRank;
    }

    public int getEssays() {
        return essays;
    }

    public void setEssays(int essays) {
        this.essays = essays;
    }

    public int getLetters() {
        return letters;
    }

    public void setLetters(int letters) {
        this.letters = letters;
    }

    public double getPreExamWritten() {
        return preExamWritten;
    }

    public void setPreExamWritten(double preExamWritten) {
        this.preExamWritten = preExamWritten;
    }

    public double getExamWritten() {
        return examWritten;
    }

    public void setExamWritten(double examWritten) {
        this.examWritten = examWritten;
    }

    public PhysicalExam getPhysicalExam() {
        return physicalExam;
    }

    public void setPhysicalExam(PhysicalExam physicalExam) {
        this.physicalExam = physicalExam;
    }
    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getGivenName() {
        return personNameComponents.getGivenName();
    }

    public String getFullName() {
        return personNameComponents.familyNameSequence();
    }

    @Override
    public String toString() {
        return String.format("CandidateResult(id=%d, userId=%s, cycleId=%d)", id, userId, cycleId);
    }
}

