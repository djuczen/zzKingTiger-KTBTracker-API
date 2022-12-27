package com.affiancesolutions.kingtiger.ktbtracker.server.model.dao;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Cycle;
import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CandidatesDAO extends DataAccessObject<Candidate, Integer> {

    private static final String CLASS_NAME = CandidatesDAO.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Resource
    private SessionContext sessionContext;


    @Override
    public Candidate create(Candidate candidate) throws PersistenceException {
        final String METHOD_NAME = "create";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, candidate);
        Candidate result = null;

        result = super.create(candidate);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @Override
    public Candidate update(Candidate candidate) {
        final String METHOD_NAME = "update";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, candidate);
        Candidate result = null;

        result = super.update(candidate);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @Override
    public void delete(Candidate candidate) {
        final String METHOD_NAME = "delete";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, candidate);

        super.delete(candidate);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    public Candidate find(Integer id) {
        final String METHOD_NAME = "find";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, id);
        Candidate result = null;

        result = super.find(Candidate.class, id);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public List<Candidate> findAll() {
        final String METHOD_NAME = "findAll";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        TypedQuery<Candidate> query = em.createNamedQuery("Candidate.findAll", Candidate.class);

        List<Candidate> result = query.getResultList();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public List<Candidate> findAllByCycle(Cycle cycle) {
        final String METHOD_NAME = "findAllByCycle";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        TypedQuery<Candidate> query = em.createNamedQuery("Candidate.findAllByCycle", Candidate.class);

        List<Candidate> result = query.setParameter("cycle", cycle).getResultList();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public List<Candidate> findForUser(String user) {
        final String METHOD_NAME = "findForUser";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, user);

        TypedQuery<Candidate> query = em.createNamedQuery("Candidate.findForUser", Candidate.class)
                .setParameter("user", user);

        List<Candidate> result = query.getResultList();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public Candidate findForUserByCycle(Cycle cycle, String user) {
        final String METHOD_NAME = "findForUserByCycle";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{cycle, user});

        TypedQuery<Candidate> query = em.createNamedQuery("Candidate.findForUserByCycle", Candidate.class)
                .setParameter("cycle", cycle)
                .setParameter("user", user);

        Candidate result = query.getSingleResult();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @PrePersist
    public void onCreate(Candidate candidate) {
        final String METHOD_NAME = "onCreate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, candidate);

        // Update the cycle metadata with the "created" timestamp
        candidate.getMetadata().setCreated(LocalDateTime.now());

        // If the caller principal is available update the "created by" user
        if (sessionContext != null && sessionContext.getCallerPrincipal() != null) {
            candidate.getMetadata().setCreatedBy(sessionContext.getCallerPrincipal().getName());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    @PreUpdate
    public void onUpdate(Candidate candidate) {
        final String METHOD_NAME = "onUpdate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, candidate);

        // Update the cycle metadata with the "modified" timestamp
        candidate.getMetadata().setModified(LocalDateTime.now());

        // If the caller principal is available update the "modified by" user
        if (sessionContext != null && sessionContext.getCallerPrincipal() != null) {
            candidate.getMetadata().setModifiedBy(sessionContext.getCallerPrincipal().getName());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }
}
