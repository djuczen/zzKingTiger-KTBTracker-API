package com.affiancesolutions.kingtiger.ktbtracker.server.model.dao;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.User;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.UserGroup;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.exception.DAONoResultException;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.exception.DAOPersistenceException;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UsersDAO extends DataAccessObject<User, String> {

    private static final String CLASS_NAME = UsersDAO.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Override
    public User create(User user) throws DAOPersistenceException {
        final String METHOD_NAME = "create";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, user);
        User result = null;

        try {
            result = super.create(user);
        } catch (Exception e) { //NOSONAR
            throw new DAOPersistenceException(e);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @Override
    public User update(User user) throws DAOPersistenceException {
        final String METHOD_NAME = "update";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, user);

        User result = null;

        try {
            result = super.update(user);
        } catch (PersistenceException e) {
            throw new DAOPersistenceException(e);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @Override
    public void delete(User user) throws DAOPersistenceException {
        final String METHOD_NAME = "update";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, user);

        try {
            // Ensure group membership is also deleted...
            for (UserGroup userGroup : user.getGroups()) {
                user.removeGroup(userGroup);
            }

            super.delete(user);
        } catch (PersistenceException e) {
            throw new DAOPersistenceException(e);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    public User find(String uid) throws DAOPersistenceException {
        final String METHOD_NAME = "find";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, uid);
        User result = null;

        try {
            result = super.find(User.class, uid);
        } catch (PersistenceException e) {
            throw new DAOPersistenceException(e);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public List<User> findAll() throws DAOPersistenceException {
        final String METHOD_NAME = "findAll";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);
        List<User> resultList = new ArrayList<>();

        TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);

        try {
            resultList = query.getResultList();
        } catch (PersistenceException e) {
            throw new DAOPersistenceException(e);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return resultList;
    }

    public User findByName(String displayName) throws DAOPersistenceException {
        final String METHOD_NAME = "findByName";
        LOGGER.exiting(CLASS_NAME, METHOD_NAME, displayName);
        User result = null;

        TypedQuery<User> query = em.createNamedQuery("User.findByName", User.class);

        try {
            result = query.setParameter("displayName", displayName).getSingleResult();
        } catch (NoResultException e) {
            throw new DAONoResultException(String.format("User '%s' does not exist", displayName));
        } catch (PersistenceException e) {
            throw new DAOPersistenceException(e);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }
}
