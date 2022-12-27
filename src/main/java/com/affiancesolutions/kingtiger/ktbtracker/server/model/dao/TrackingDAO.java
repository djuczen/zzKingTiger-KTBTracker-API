package com.affiancesolutions.kingtiger.ktbtracker.server.model.dao;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.Statistics;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Tracking;
import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;

import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.*;

@Stateless
public class TrackingDAO extends DataAccessObject<Tracking, Integer> {

    private static final String CLASS_NAME = TrackingDAO.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Resource
    private SessionContext sessionContext;


    @Override
    public Tracking create(Tracking tracking) throws PersistenceException {
        final String METHOD_NAME = "create";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, tracking);
        Tracking result = null;

        result = super.create(tracking);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @Override
    public Tracking update(Tracking tracking) throws PersistenceException {
        final String METHOD_NAME = "update";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, tracking);

        Tracking result = super.update(tracking);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @Override
    public void delete(Tracking tracking) throws PersistenceException {
        final String METHOD_NAME = "delete";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, tracking);

        super.delete(tracking);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    public Tracking find(int id) throws PersistenceException {
        final String METHOD_NAME = "find";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, id);

        Tracking result = super.find(Tracking.class, id);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public Tracking findByCandidate(Candidate candidate, LocalDate trackingDate) throws PersistenceException {
        final String METHOD_NAME = "findByCandidate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidate, trackingDate});
        Tracking result = null;

        TypedQuery<Tracking> query = em.createNamedQuery("Tracking.findByCandidate", Tracking.class)
                .setParameter(PARAM_CANDIDATE, candidate)
                .setParameter(PARAM_TRACKING_DATE, trackingDate);

        try {
            result = query.getSingleResult();
        } catch (NoResultException e) { //NOSONAR
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public List<Tracking> findAllByCandidate(Candidate candidate) {
        final String METHOD_NAME = "findAllByCandidate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, candidate);

        TypedQuery<Tracking> query = em.createNamedQuery("Tracking.findAllByCandidate", Tracking.class)
                .setParameter(PARAM_CANDIDATE, candidate);

        List<Tracking> result = query.getResultList();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public List<Tracking> findRangeByCandidate(Candidate candidate, LocalDate startDate, LocalDate endDate) {
        final String METHOD_NAME = "findRangeByCandidate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidate, startDate, endDate});

        TypedQuery<Tracking> query = em.createNamedQuery("Tracking.findRangeByCandidate", Tracking.class)
                .setParameter(PARAM_CANDIDATE, candidate)
                .setParameter(PARAM_FROM_DATE, startDate)
                .setParameter(PARAM_TO_DATE, endDate);

        List<Tracking> result = query.getResultList();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public Statistics getStatistics(Candidate candidate, LocalDate startDate, LocalDate endDate) {
        final String METHOD_NAME = "getStatistics";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidate, startDate, endDate});
/*
        Query query = em.createNativeQuery(String.format("""
                                SELECT
                                        CAST(COALESCE(SUM(t.MILES), 0.0) AS DECIMAL(10,2)) AS miles,
                                        COALESCE(SUM(t.PUSHUPS), 0) AS pushUps,
                                        COALESCE(SUM(t.SITUPS), 0) AS sitUps,
                                        COALESCE(SUM(t.BURPEES), 0) AS burpees,
                                        COALESCE(SUM(t.KICKS), 0) AS kicks,
                                        COALESCE(SUM(t.POOMSAE), 0) AS poomsae,
                                        COALESCE(SUM(t.SELF_DEFENSE), 0) AS selfDefense,
                                        CAST(COALESCE(SUM(t.SPARRING), 0.0) AS DECIMAL(10,2)) AS sparring,
                                        CAST(COALESCE(SUM(t.JUMPS), 0.0) AS DECIMAL(10,2)) AS jumps,
                                        COALESCE(SUM(t.PULLUPS), 0) AS pullUps,
                                        COALESCE(SUM(t.PLANKS), 0) AS planks,
                                        COALESCE(SUM(t.ROLLS_FALLS), 0) AS rollsFalls,
                                        COALESCE(SUM(t.CLASS_SATURDAY), 0) AS classSaturday,
                                        COALESCE(SUM(t.CLASS_WEEKDAY), 0) AS classWeekDay,
                                        COALESCE(SUM(t.CLASS_PMAA), 0) AS classPMAA,
                                        COALESCE(SUM(t.CLASS_SPARRING), 0) AS classSparring,
                                        COALESCE(SUM(t.CLASS_MASTERQ), 0) AS classMasterQ,
                                        COALESCE(SUM(t.CLASS_DREAMTEAM), 0) AS classDreamTeam,
                                        COALESCE(SUM(t.CLASS_HYPERPRO), 0) AS classHyperPro,
                                        CAST(COALESCE(SUM(t.MEDITATION), 0.0) AS DECIMAL(10,2)) AS meditation,
                                        COALESCE(SUM(t.RAOK), 0) AS randomActs,
                                        COALESCE(SUM(t.MENTOR), 0) AS mentor,
                                        COALESCE(SUM(t.MENTEE), 0) AS mentee,
                                        COALESCE(SUM(t.LEADERSHIP), 0) AS leadership,
                                        COALESCE(SUM(t.LEADERSHIP2), 0) AS leadership2,
                                        (SELECT COUNT(DISTINCT DATE(j.CREATED))
                                            FROM JOURNAL_POSTS AS j
                                            WHERE j.CANDIDATE_ID = %3$d
                                                AND DATE(j.CREATED) BETWEEN '%1$s' AND '%2$s' + INTERVAL 1 DAY
                                        ) AS journals
                                    FROM TRACKING AS t
                                    WHERE t.TRACKING_DATE BETWEEN '%1$s' AND '%2$s'
                                        AND t.CANDIDATE_ID = %3$d
                                """,
                        startDate, endDate, candidate.getId()), "Tracking.getStatistics_Mapping");
        Statistics result = (Statistics) query.getSingleResult();
        result.setCandidateId(candidate.getId());
        result.setStartDate(startDate);
        result.setEndDate(endDate);
*/

       long cycleDays = candidate.getCycle().getCycleStart()
               .until(candidate.getCycle().getCycleEnd().plusDays(1), ChronoUnit.DAYS);
       long statsDays = startDate.until(endDate.plusDays(1), ChronoUnit.DAYS);
       double statsFactor = (double) statsDays / (double) cycleDays;
       LOGGER.finest(String.format("Factor: %d, %d, %f", cycleDays, statsDays, statsFactor));

       TypedQuery<Statistics> query = em.createNamedQuery("Tracking.getStatistics_X", Statistics.class)
               .setParameter(PARAM_CANDIDATE, candidate)
               .setParameter(PARAM_FROM_DATE, startDate)
               .setParameter(PARAM_TO_DATE, endDate);
       Statistics statistics = query.getSingleResult();

       statistics.setCandidateId(candidate.getId());
       statistics.setStartDate(startDate);
       statistics.setEndDate(endDate);
       statistics.setOverall(candidate.getCycle().getRequirements(), statsFactor);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, statistics);
        return statistics;
    }

    @PrePersist
    public void onCreate(Tracking tracking) {
        final String METHOD_NAME = "onCreate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, tracking);

        // Update the cycle metadata with the "created" timestamp
        tracking.getMetadata().setCreated(LocalDateTime.now());

        // If the caller principal is available update the "created by" user
        if (sessionContext != null && sessionContext.getCallerPrincipal() != null) {
            tracking.getMetadata().setCreatedBy(sessionContext.getCallerPrincipal().getName());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    @PreUpdate
    public void onUpdate(Tracking tracking) {
        final String METHOD_NAME = "onUpdate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, tracking);

        // Update the cycle metadata with the "modified" timestamp
        tracking.getMetadata().setModified(LocalDateTime.now());

        // If the caller principal is available update the "modified by" user
        if (sessionContext != null && sessionContext.getCallerPrincipal() != null) {
            tracking.getMetadata().setModifiedBy(sessionContext.getCallerPrincipal().getName());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }
}
