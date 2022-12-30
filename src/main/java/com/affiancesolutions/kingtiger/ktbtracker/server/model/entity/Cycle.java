package com.affiancesolutions.kingtiger.ktbtracker.server.model.entity;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CyclesDAO;
import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@EntityListeners(CyclesDAO.class)
@Table(name = "CYCLES", indexes = {
        @Index(name = "CYCLE_ALIAS_NDX", columnList = "ALIAS", unique = true)
})
@NamedQueries({
        @NamedQuery(name = "Cycle.findAll",
                query = "SELECT t FROM Cycle AS t "
                        + "ORDER BY t.id DESC"),
        @NamedQuery(name = "Cycle.findCurrent",
                query = "SELECT t FROM Cycle AS t"
                        + " WHERE CURRENT_DATE BETWEEN"
                        + " (CASE WHEN t.cyclePreStart < t.cycleStart"
                        + " THEN t.cyclePreStart ELSE t.cycleStart END)"
                        + " AND "
                        + " (CASE WHEN t.cyclePostEnd > t.cycleEnd"
                        + " THEN t.cyclePostEnd ELSE t.cycleEnd END)"),
        @NamedQuery(name = "Cycle.findByAlias",
                query = "SELECT t FROM Cycle t"
                        + " WHERE t.alias = :alias")
})
public class Cycle implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = 2398091363083983802L;

    /**
     * The auto-generated ID for this entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    /**
     * Title assigned to the cycle, typically {Spring|Fall} 20XX.
     */
    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "ALIAS", nullable = false)
    private String alias;

    /**
     * The offical start date of the cycle.
     */
    @Column(name = "CYCLE_START", nullable = false)
    private LocalDate cycleStart;

    /**
     * The official end date of the cycle.
     */
    @Column(name = "CYCLE_END", nullable = false)
    private LocalDate cycleEnd;

    /**
     * A pre-start date that candidates can start to track requirements early.
     */
    @Column(name = "CYCLE_PRE_START")
    private LocalDate cyclePreStart;

    /**
     * A post-end date that candidate are provided extra time to complete requirements.
     */
    @Column(name = "CYCLE_POST_END")
    private LocalDate cyclePostEnd;

    /**
     * The day of the week (ISO) when a cycle week begins, 1 = Monday, 6 = Saturday.
     */
    @Column(name = "CYCLE_WEEK_START", nullable = false)
    private int cycleWeekStart;

    /**
     * Requirements (physical, classes, other) for the cycle. These particular values indicate the "goal" to be
     * reached for each requirement, with a 0 value meaning it is not required.
     */
    @Embedded
    private Requirements requirements = new Requirements();

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

    public int getCycleWeekStart() {
        return cycleWeekStart;
    }

    public void setCycleWeekStart(int cycleWeekStart) {
        this.cycleWeekStart = cycleWeekStart;
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

    public String generateAlias(String title) {
        String generated = title != null ? title : "";
        generated = generated.toLowerCase().replaceAll("[()'" + "\"\\s_]", "-");

        return generated;
    }

    @Override
    public String toString() {
        return String.format("Cycle(id=%d, alias=%s, title='%s')", id, alias, title);
    }

    @PrePersist
    private void prePersist() {
        if (this.alias == null) {
            this.alias = generateAlias(this.title);
        }
    }
}
