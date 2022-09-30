package com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.exception;

import jakarta.ejb.ApplicationException;
import jakarta.persistence.PersistenceException;

@ApplicationException
public class DAOPersistenceException extends PersistenceException {

    public DAOPersistenceException() {
        super();
    }

    public DAOPersistenceException(String message) {
        super(message);
    }

    public DAOPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOPersistenceException(Throwable cause) {
        super(cause);
    }
}
