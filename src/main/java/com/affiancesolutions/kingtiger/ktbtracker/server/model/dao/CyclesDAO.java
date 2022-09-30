package com.affiancesolutions.kingtiger.ktbtracker.server.model.dao;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.Cycle;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.Metadata;
import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.TypedQuery;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CyclesDAO extends DataAccessObject<Cycle, Long> {

    private static final String CLASS_NAME = CyclesDAO.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Resource
    private SessionContext sessionContext;


    @Override
    public Cycle create(Cycle cycle) throws PersistenceException {
        final String METHOD_NAME = "create";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, cycle);
        Cycle result = null;

        result = super.create(cycle);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @Override
    public Cycle update(Cycle cycle) throws PersistenceException {
        final String METHOD_NAME = "update";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, cycle);

        cycle.setMetaData(new Metadata(sessionContext.getCallerPrincipal().getName()));

        Cycle result = super.update(cycle);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @Override
    public void delete(Cycle cycle) throws PersistenceException {
        final String METHOD_NAME = "update";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, cycle);

        super.delete(cycle);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    public Cycle find(Long id) throws PersistenceException {
        final String METHOD_NAME = "find";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, id);

        Cycle result = super.find(Cycle.class, id);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public List<Cycle> findAll() throws PersistenceException {
        final String METHOD_NAME = "findAll";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        TypedQuery<Cycle> query = em.createNamedQuery("Cycle.findAll", Cycle.class);

        List<Cycle> resultList = query.getResultList();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return resultList;
    }

    /**
     * Returns the currently active cycle (spans the current date) or null if no cycle is active.
     *
     * @return the currently active {@link Cycle} entity or null
     * @throws IOException
     * @throws NoResultException
     */
    public Cycle findCurrent() throws NoResultException {
        final String METHOD_NAME = "findCurrent";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);
        Cycle result = null;

        try {
            TypedQuery<Cycle> query = em.createNamedQuery("Cycle.findCurrent", Cycle.class);

            result = query.getSingleResult();
        } catch (NoResultException e) { //NOSONAR
            LOGGER.finest("Cycle.findCurrent did not return a result");
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    /**
     * Returns the currently active cycle (spans the current date) or null if no cycle is active.
     *
     * @return the currently active {@link Cycle} entity or null
     * @throws IOException
     * @throws NoResultException
     */
    public Cycle findByAlias(String alias) throws NoResultException {
        final String METHOD_NAME = "findByAlias";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, alias);
        Cycle result = null;

        try {
            TypedQuery<Cycle> query = em.createNamedQuery("Cycle.findByAlias", Cycle.class)
                    .setParameter("alias", alias);

            result = query.getSingleResult();
        } catch (NoResultException e) { //NOSONAR
            LOGGER.finest(String.format("Cycle was not found by alias '%s'", alias));
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @PrePersist
    public void onPrePersist(Cycle cycle) {
        final String METHOD_NAME = "onPrePersist";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, cycle);

        LOGGER.fine(String.format("Cycle: %s, SecurityContext: %s, Principal: %s", cycle, sessionContext, sessionContext.getCallerPrincipal().getName()));

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }
}
