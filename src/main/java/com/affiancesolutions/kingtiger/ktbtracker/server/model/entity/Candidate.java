package com.affiancesolutions.kingtiger.ktbtracker.server.model.entity;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CandidatesDAO;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@EntityListeners(CandidatesDAO.class)
@Table(name = "CANDIDATES", indexes = {
        @Index(name = "CANDIDATE_CYCLE_NDX", columnList = "ID, CYCLE_ID", unique = true)
})
@NamedQueries({
        @NamedQuery(name = "Candidate.findAll",
                query = "SELECT t FROM Candidate AS t "
                        + "ORDER BY t.cycle.id DESC, t.id DESC"),
        @NamedQuery(name = "Candidate.findAllByCycle",
                query = "SELECT t FROM Candidate AS t "
                        + "WHERE t.cycle = :cycle "
                        + "ORDER BY t.id DESC"),
        @NamedQuery(name = "Candidate.findForUser",
                query = "SELECT t FROM Candidate AS t "
                        + "WHERE t.user.id = :user "
                        + "ORDER BY t.id DESC"),
        @NamedQuery(name = "Candidate.findForUserByCycle",
                query = "SELECT t FROM Candidate AS t "
                        + "WHERE t.user.id = :user "
                        + "AND t.cycle = :cycle ")
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    /**
     * Object Relationship Mapping (ORM) many-to-one relationship to the USERS table (User).
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    /**
     * Object Relationship Mapping (ORM) many-to-one relationship to the cycle (Cycle).
     */
    @ManyToOne
    @JoinColumn(name = "CYCLE_ID", nullable = false)
    private Cycle cycle;

    /**
     * Completed required essays (varies depending on belt rank)
     */
    @Column(name = "ESSAYS", nullable = false)
    private int essays;

    /**
     * Completed required recommendation letters (varies depending on belt rank).
     */
    @Column(name = "LETTERS", nullable = false)
    private int letters;

    /**
     * Score obtained on the written pre-exam.
     */
    @Column(name = "PRE_EXAM_WRITTEN", nullable = false)
    private double preExamWritten;

    /**
     * Score obtained on the written exam.
     */
    @Column(name = "EXAM_WRITTEN", nullable = false)
    private double examWritten;

    /**
     * Physical exam results (pre- and official physical exam results).
     */
    @Embedded
    private PhysicalExam physicalExam = new PhysicalExam();

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
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

    @Override
    public String toString() {
        return String.format("Candidate(id=%d, user_id=%s, cycle=%s)", id, user.getId(), cycle.getId());
    }
}
