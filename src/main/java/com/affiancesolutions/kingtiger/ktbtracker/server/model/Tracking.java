package com.affiancesolutions.kingtiger.ktbtracker.server.model;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.TrackingDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.Statistics;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@EntityListeners({TrackingDAO.class})
@Table(name = "TRACKING", indexes = {
        @Index(name = "TRACKING_DATE_NDX", columnList = "TRACKING_DATE, CANDIDATE_ID", unique = true)
})
@NamedQueries({
        @NamedQuery(name = "Tracking.findAllByCandidate",
                query = "SELECT t FROM Tracking t"
                        + " WHERE t.candidate = :candidate"
                        + " ORDER BY t.trackingDate ASC"),
        @NamedQuery(name = "Tracking.findByCandidate",
                query = "SELECT t FROM Tracking t"
                        + " WHERE t.trackingDate = :tracking_date"
                        + " AND t.candidate = :candidate"),
        @NamedQuery(name = "Tracking.findRangeByCandidate",
                query = "SELECT t FROM Tracking t"
                        + " WHERE t.trackingDate BETWEEN :from_date AND :to_date"
                        + " AND t.candidate = :candidate"
                        + " ORDER BY t.trackingDate ASC"),
        @NamedQuery(name = "Tracking.getStatistics_X",
                query = "SELECT NEW com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.Statistics("
                        + "     COALESCE(SUM(t.requirements.miles), 0),"
                        + "     COALESCE(SUM(t.requirements.pushUps), 0),"
                        + "     COALESCE(SUM(t.requirements.sitUps), 0),"
                        + "     COALESCE(SUM(t.requirements.burpees), 0),"
                        + "     COALESCE(SUM(t.requirements.kicks), 0),"
                        + "     COALESCE(SUM(t.requirements.poomsae), 0),"
                        + "     COALESCE(SUM(t.requirements.selfDefense), 0),"
                        + "     COALESCE(SUM(t.requirements.sparring), 0),"
                        + "     COALESCE(SUM(t.requirements.jumps), 0),"
                        + "     COALESCE(SUM(t.requirements.pullUps), 0),"
                        + "     COALESCE(SUM(t.requirements.planks), 0),"
                        + "     COALESCE(SUM(t.requirements.rollsFalls), 0),"
                        + "     COALESCE(SUM(t.requirements.classSaturday), 0),"
                        + "     COALESCE(SUM(t.requirements.classWeekday), 0),"
                        + "     COALESCE(SUM(t.requirements.classPMAA), 0),"
                        + "     COALESCE(SUM(t.requirements.classSparring), 0),"
                        + "     COALESCE(SUM(t.requirements.classMasterQ), 0),"
                        + "     COALESCE(SUM(t.requirements.classDreamTeam), 0),"
                        + "     COALESCE(SUM(t.requirements.classHyperPro), 0),"
                        + "     COALESCE(SUM(t.requirements.meditation), 0),"
                        + "     COALESCE(SUM(t.requirements.randomActs), 0),"
                        + "     COALESCE(SUM(t.requirements.mentor), 0),"
                        + "     COALESCE(SUM(t.requirements.mentee), 0),"
                        + "     COALESCE(SUM(t.requirements.leadership), 0),"
                        + "     COALESCE(SUM(t.requirements.leadership2), 0),"
                        + "     (SELECT COUNT(DISTINCT CAST(j.metadata.created AS DATE))"
                        + "      FROM JournalPost AS j"
                        + "      WHERE j.candidate = :candidate"
                        + "        AND CAST(j.metadata.created AS DATE) BETWEEN :from_date AND :to_date"
                        + "     ))"
                        + " FROM Tracking t"
                        + " WHERE t.trackingDate BETWEEN :from_date AND :to_date"
                        + "  AND t.candidate = :candidate"),
        @NamedQuery(name = "Tracking.getStatistics_RAW",
                query = "SELECT"
                        + " COALESCE(SUM(t.requirements.miles), 0),"
                        + " COALESCE(SUM(t.requirements.pushUps), 0),"
                        + " COALESCE(SUM(t.requirements.sitUps), 0),"
                        + " COALESCE(SUM(t.requirements.burpees), 0),"
                        + " COALESCE(SUM(t.requirements.kicks), 0),"
                        + " COALESCE(SUM(t.requirements.poomsae), 0),"
                        + " COALESCE(SUM(t.requirements.selfDefense), 0),"
                        + " COALESCE(SUM(t.requirements.sparring), 0),"
                        + " COALESCE(SUM(t.requirements.jumps), 0),"
                        + " COALESCE(SUM(t.requirements.pullUps), 0),"
                        + " COALESCE(SUM(t.requirements.planks), 0),"
                        + " COALESCE(SUM(t.requirements.rollsFalls), 0),"
                        + " COALESCE(SUM(t.requirements.classSaturday), 0),"
                        + " COALESCE(SUM(t.requirements.classWeekday), 0),"
                        + " COALESCE(SUM(t.requirements.classPMAA), 0),"
                        + " COALESCE(SUM(t.requirements.classSparring), 0),"
                        + " COALESCE(SUM(t.requirements.classMasterQ), 0),"
                        + " COALESCE(SUM(t.requirements.classDreamTeam), 0),"
                        + " COALESCE(SUM(t.requirements.classHyperPro), 0),"
                        + " COALESCE(SUM(t.requirements.meditation), 0),"
                        + " COALESCE(SUM(t.requirements.randomActs), 0),"
                        + " COALESCE(SUM(t.requirements.mentor), 0),"
                        + " COALESCE(SUM(t.requirements.mentee), 0),"
                        + " COALESCE(SUM(t.requirements.leadership), 0),"
                        + " COALESCE(SUM(t.requirements.leadership2), 0),"
                        + " (SELECT COUNT(DISTINCT CAST(j.metadata.created AS DATE))"
                        + "  FROM JournalPost AS j"
                        + "  WHERE j.candidate = :candidate"
                        + "   AND CAST(j.metadata.created AS DATE) BETWEEN :from_date AND :to_date"
                        + " )"
                        + " FROM Tracking t"
                        + " WHERE t.trackingDate BETWEEN :from_date AND :to_date"
                        + "  AND t.candidate = :candidate")
})
/*
@NamedNativeQueries(
        @NamedNativeQuery(name = "Tracking.getStatistics",
                query = "SELECT "
                        + " CAST(COALESCE(SUM(MILES), 0.0) AS DECIMAL(10,2)) AS miles,"
                        + " COALESCE(SUM(PUSHUPS), 0) AS pushUps,"
                        + " COALESCE(SUM(SITUPS), 0) AS sitUps,"
                        + " COALESCE(SUM(BURPEES), 0) AS burpees,"
                        + " COALESCE(SUM(KICKS), 0) AS kicks,"
                        + " COALESCE(SUM(POOMSAE), 0) AS poomsae,"
                        + " COALESCE(SUM(SELF_DEFENSE), 0) AS selfDefense,"
                        + " CAST(COALESCE(SUM(SPARRING), 0.0) AS DECIMAL(10,2)) AS sparring,"
                        + " CAST(COALESCE(SUM(JUMPS), 0.0) AS DECIMAL(10,2)) AS jumps,"
                        + " COALESCE(SUM(PULLUPS), 0) AS pullUps,"
                        + " COALESCE(SUM(PLANKS), 0) AS planks,"
                        + " COALESCE(SUM(ROLLS_FALLS), 0) AS rollsFalls,"
                        + " COALESCE(SUM(CLASS_SATURDAY), 0) AS classSaturday,"
                        + " COALESCE(SUM(CLASS_WEEKDAY), 0) AS classWeekDay,"
                        + " COALESCE(SUM(CLASS_PMAA), 0) AS classPMAA,"
                        + " COALESCE(SUM(CLASS_SPARRING), 0) AS classSparring,"
                        + " COALESCE(SUM(CLASS_MASTERQ), 0) AS classMasterQ,"
                        + " COALESCE(SUM(CLASS_DREAMTEAM), 0) AS classDreamTeam,"
                        + " COALESCE(SUM(CLASS_HYPERPRO), 0) AS classHyperPro,"
                        + " CAST(COALESCE(SUM(MEDITATION), 0.0) AS DECIMAL(10,2)) AS meditation,"
                        + " COALESCE(SUM(RAOK), 0) AS randomActs,"
                        + " COALESCE(SUM(MENTOR), 0) AS mentor,"
                        + " COALESCE(SUM(MENTEE), 0) AS mentee,"
                        + " COALESCE(SUM(LEADERSHIP), 0) AS leadership,"
                        + " COALESCE(SUM(LEADERSHIP2), 0) AS leadership2"
                        + " FROM TRACKING t"
                        + " WHERE t.TRACKING_DATE BETWEEN '%s' AND '%s'"
                        + " AND t.CANDIDATE_ID = %d",
                resultSetMapping = "Tracking.getStatistics_Mapping")
)
*/
@SqlResultSetMapping(name = "Tracking.getStatistics_Mapping",
        classes = @ConstructorResult(
                targetClass = Statistics.class,
                columns = {
                        @ColumnResult(name = "miles", type = Double.class),
                        @ColumnResult(name = "pushUps", type = Long.class),
                        @ColumnResult(name = "sitUps", type = Long.class),
                        @ColumnResult(name = "burpees", type = Long.class),
                        @ColumnResult(name = "kicks", type = Long.class),
                        @ColumnResult(name = "poomsae", type = Long.class),
                        @ColumnResult(name = "selfDefense", type = Long.class),
                        @ColumnResult(name = "sparring", type = Double.class),
                        @ColumnResult(name = "jumps", type = Double.class),
                        @ColumnResult(name = "pullUps", type = Long.class),
                        @ColumnResult(name = "planks", type = Long.class),
                        @ColumnResult(name = "rollsFalls", type = Long.class),
                        @ColumnResult(name = "classSaturday", type = Long.class),
                        @ColumnResult(name = "classWeekday", type = Long.class),
                        @ColumnResult(name = "classPMAA", type = Long.class),
                        @ColumnResult(name = "classSparring", type = Long.class),
                        @ColumnResult(name = "classMasterQ", type = Long.class),
                        @ColumnResult(name = "classDreamTeam", type = Long.class),
                        @ColumnResult(name = "classHyperPro", type = Long.class),
                        @ColumnResult(name = "meditation", type = Double.class),
                        @ColumnResult(name = "randomActs", type = Long.class),
                        @ColumnResult(name = "mentor", type = Long.class),
                        @ColumnResult(name = "mentee", type = Long.class),
                        @ColumnResult(name = "leadership", type = Long.class),
                        @ColumnResult(name = "leadership2", type = Long.class),
                        @ColumnResult(name = "journals", type = Long.class)
                }))
public class Tracking implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = 5900044619481012717L;

    /**
     * The auto-generated ID for this entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The date the candidate is tracking (this along with the candidate form a unique index).
     */
    @Column(name = "TRACKING_DATE", nullable = false)
    private LocalDate trackingDate;

    /**
     * Object Relational Model (ORM) relationship to the candidate (Candidate) being tracked.
     */
    @ManyToOne
    @JoinColumn(name = "CANDIDATE_ID", nullable = false)
    private Candidate candidate;

    /**
     * Object Relational Model (ORM) relationship to the cycle (Cycle) being tracked.
     */
    @ManyToOne
    @JoinColumn(name = "CYCLE_ID", nullable = false)
    private Cycle cycle;

    @Embedded
    private Requirements requirements = new Requirements();

    /**
     * Metadata (created, last modified) for the tracking record (Embedded)
     */
    @Embedded
    private Metadata metadata = new Metadata();

    public Tracking() {

    }

    public Tracking(Cycle cycle, Candidate candidate, LocalDate trackingDate) {
        this.cycle = cycle;
        this.candidate = candidate;
        this.trackingDate = trackingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getTrackingDate() {
        return trackingDate;
    }

    public void setTrackingDate(LocalDate trackingDate) {
        this.trackingDate = trackingDate;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
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

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return String.format("Tracking(id=%d, cycle=%s, candidate=%s, trackingDate=%s)",
                getId(), getCycle(), getCandidate(), getTrackingDate());
    }
}
