package com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class DAOEntityExistsException extends DAOPersistenceException {

    public DAOEntityExistsException() {
        super();
    }

    public DAOEntityExistsException(String message) {
        super(message);
    }

    public DAOEntityExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOEntityExistsException(Throwable cause) {
        super(cause);
    }
}
