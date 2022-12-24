package com.affiancesolutions.kingtiger.ktbtracker.server.model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.io.IOException;
import java.util.logging.Logger;

public class DataAccessObject<T, K> {

    private static final String CLASS_NAME = DataAccessObject.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @PersistenceContext(unitName = "KingTiger-KTBTracker-API")
    protected EntityManager em;


    /**
     * Implements the CREATE (C) primitive of the CRUD data model.
     *
     * @param t the entity to create
     * @return
     * @throws Exception
     */
    public T create(T t) throws PersistenceException {
        final String METHOD_NAME = "create";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{t});

        em.persist(t);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, t);
        return t;
    }

    /**
     * Implements the READ (R) primitive of the CRUD data model.
     *
     * @param t the entity class
     * @param k the key to locate in the database
     * @return the located entity or null
     * @throws IOException
     */
    public T find(Class<T> t, K k) {
        final String METHOD_NAME = "find";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{t, k});

        T result = (T) em.find((Class<T>) t, k);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    /**
     * Implements the UPDATE (U) primitive of the CRUD data model.
     *
     * @param t the entity class
     * @return the updated (merged) entity
     * @throws IOException
     */
    public T update(T t) throws PersistenceException {
        final String METHOD_NAME = "update";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{t});

        T result = (T) em.merge(t);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }


    /**
     * Implements the DELETE (D) primitive of the CRUD data model.
     *
     * @param t the instance of the entity class
     * @throws IOException
     */
    public void delete(T t) throws PersistenceException {
        final String METHOD_NAME = "delete";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{t});

        // Before removing, ensure the entity is attached
        if (!em.contains(t)) {
            em.merge(t);
        }

        em.remove(t);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }
}
