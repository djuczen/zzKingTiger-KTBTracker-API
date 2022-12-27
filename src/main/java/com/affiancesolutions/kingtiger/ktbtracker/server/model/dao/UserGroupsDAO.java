package com.affiancesolutions.kingtiger.ktbtracker.server.model.dao;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.User;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.UserGroup;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.exception.DAOEntityExistsException;
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
public class UserGroupsDAO extends DataAccessObject<UserGroup, Integer> {

    private static final String CLASS_NAME = UserGroupsDAO.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Override
    public UserGroup create(UserGroup userGroup) throws DAOPersistenceException {
        final String METHOD_NAME = "create";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, userGroup);
        UserGroup result = null;

        if (findByName(userGroup.getName()) != null) {
            throw new DAOEntityExistsException(String.format("User group '%s' already exists", userGroup.getName()));
        }

        try {
            result = super.create(userGroup);
        } catch (PersistenceException e) {
            throw new DAOPersistenceException(e);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @Override
    public UserGroup update(UserGroup userGroup) throws DAOPersistenceException {
        final String METHOD_NAME = "update";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, userGroup);
        UserGroup result = null;

        try {
            result = super.update(userGroup);
        } catch (PersistenceException e) {
            throw new DAOPersistenceException(e);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    @Override
    public void delete(UserGroup userGroup) throws DAOPersistenceException {
        final String METHOD_NAME = "update";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, userGroup);

        try {
            super.delete(userGroup);
        } catch (PersistenceException e) {
            throw new DAOPersistenceException(e);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    public void deleteByName(String groupName) throws DAOPersistenceException {
        final String METHOD_NAME = "deleteByName";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, groupName);

        UserGroup userGroup = findByName(groupName);
        if (userGroup == null) {
            throw new DAONoResultException(String.format("User group '%s' not found for delete", groupName));
        }

        // Ensure group membership is also deleted...
        for (User user : userGroup.getUsers()) {
            userGroup.removeUser(user);
        }

        delete(userGroup);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    public UserGroup find(int id) {
        final String METHOD_NAME = "find";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, id);

        UserGroup result = super.find(UserGroup.class, id);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public List<UserGroup> findAll() throws DAOPersistenceException {
        final String METHOD_NAME = "findAll";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);
        List<UserGroup> resultList = new ArrayList<>();

        TypedQuery<UserGroup> query = em.createNamedQuery("UserGroup.findAll", UserGroup.class);

        try {
            resultList = query.getResultList();
        } catch (PersistenceException e) {
            throw new DAOPersistenceException(e);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return resultList;
    }

    public UserGroup findByName(String groupName) throws DAOPersistenceException {
        final String METHOD_NAME = "findByName";
        LOGGER.exiting(CLASS_NAME, METHOD_NAME, new Object[]{groupName});
        UserGroup result = null;

        TypedQuery<UserGroup> query = em.createNamedQuery("UserGroup.findByName", UserGroup.class);

        try {
            result = query.setParameter("groupName", groupName).getSingleResult();
        } catch (NoResultException e) { //NOSONAR
            //
        } catch (PersistenceException e) {
            throw new DAOPersistenceException(e);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return result;
    }
}
