package com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class DAONoResultException extends DAOPersistenceException {

    public DAONoResultException() {
        super();
    }

    public DAONoResultException(String message) {
        super(message);
    }
}
