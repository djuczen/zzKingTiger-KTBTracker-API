package com.affiancesolutions.kingtiger.ktbtracker.server.model.dao;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.JournalPost;
import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.TypedQuery;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Stateless
public class JournalPostsDAO extends DataAccessObject<JournalPost, Integer> {

    private static final String CLASS_NAME = JournalPostsDAO.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Resource
    private SessionContext sessionContext;


    @Override
    public JournalPost create(JournalPost journalPost) throws PersistenceException {
        final String METHOD_NAME = "create";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, journalPost);

        JournalPost result = super.create(journalPost);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @Override
    public JournalPost update(JournalPost journalPost) throws PersistenceException {
        final String METHOD_NAME = "update";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, journalPost);

        JournalPost result = super.update(journalPost);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @Override
    public void delete(JournalPost journalPost) throws PersistenceException {
        final String METHOD_NAME = "delete";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, journalPost);

        super.delete(journalPost);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    public JournalPost find(int id) throws PersistenceException {
        final String METHOD_NAME = "find";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, id);

        JournalPost result = super.find(JournalPost.class, id);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public List<JournalPost> findAll() throws PersistenceException {
        final String METHOD_NAME = "findAll";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        TypedQuery<JournalPost> query = em.createNamedQuery("JournalPost.findAll", JournalPost.class);

        List<JournalPost> resultList = query.getResultList();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return resultList;
    }

    public List<JournalPost> findAllByCandidate(Candidate candidate) throws PersistenceException {
        final String METHOD_NAME = "findAllByCandidate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, candidate);

        TypedQuery<JournalPost> query = em.createNamedQuery("JournalPost.findAllByCandidate", JournalPost.class)
                .setParameter("candidate", candidate);

        List<JournalPost> resultList = query.getResultList();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return resultList;
    }

    public List<JournalPost> findRangeByCandidate(Candidate candidate, LocalDate startDate, LocalDate endDate)
            throws PersistenceException {
        final String METHOD_NAME = "findRangeByCandidate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidate, startDate, endDate});

        TypedQuery<JournalPost> query = em.createNamedQuery("JournalPost.findRangeByCandidate", JournalPost.class)
                .setParameter("candidate", candidate)
                .setParameter("from_date", LocalDateTime.from(startDate.atStartOfDay()))
                .setParameter("to_date", LocalDateTime.from(endDate.plusDays(1).atStartOfDay()));

        List<JournalPost> resultList = query.getResultList();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return resultList;
    }

    public Long countDaysJournalByCandidate(Candidate candidate, LocalDate startDate, LocalDate endDate)
            throws PersistenceException {
        final String METHOD_NAME = "countDaysJournaledByCandidate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidate, startDate, endDate});

        TypedQuery<Long> query = em.createNamedQuery("JournalPost.countDaysJournaledByCandidate", Long.class)
                .setParameter("candidate", candidate)
                .setParameter("from_date", startDate)
                .setParameter("to_date", endDate);

        Long result = query.getSingleResult();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public List<LocalDate> findDaysJournalByCandidate(Candidate candidate, LocalDate startDate, LocalDate endDate)
            throws PersistenceException {
        final String METHOD_NAME = "findDaysJournaledByCandidate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidate, startDate, endDate});

        TypedQuery<Date> query = em.createNamedQuery("JournalPost.findDaysJournaledByCandidate", Date.class)
                .setParameter("candidate", candidate)
                .setParameter("from_date", startDate)
                .setParameter("to_date", endDate);

        List<Date> resultList = query.getResultList();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return resultList.stream().map(Date::toLocalDate).collect(Collectors.toList());
    }

    @PrePersist
    public void onCreate(JournalPost journalPost) {
        final String METHOD_NAME = "onCreate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, journalPost);

        // Update the cycle metadata with the "created" timestamp
        journalPost.getMetadata().setCreated(LocalDateTime.now());

        // If the caller principal is available update the "created by" user
        if (sessionContext != null && sessionContext.getCallerPrincipal() != null) {
            journalPost.getMetadata().setCreatedBy(sessionContext.getCallerPrincipal().getName());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    @PreUpdate
    public void onUpdate(JournalPost journalPost) {
        final String METHOD_NAME = "onUpdate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, journalPost);

        // Update the cycle metadata with the "modified" timestamp
        journalPost.getMetadata().setModified(LocalDateTime.now());

        // If the caller principal is available update the "modified by" user
        if (sessionContext != null && sessionContext.getCallerPrincipal() != null) {
            journalPost.getMetadata().setModifiedBy(sessionContext.getCallerPrincipal().getName());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }
}
