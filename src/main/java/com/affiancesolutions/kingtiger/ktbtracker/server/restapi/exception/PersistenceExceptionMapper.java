package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.exception;

import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {

    private static final String CLASS_NAME = PersistenceExceptionMapper.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);


    /**
     * Map an exception to a {@link Response}. Returning {@code null} results in a
     * {@link Response.Status#NO_CONTENT} response. Throwing a runtime exception results in a
     * {@link Response.Status#INTERNAL_SERVER_ERROR} response.
     *
     * @param exception the exception to map to a response.
     * @return a response mapped from the supplied exception.
     */
    @Override
    public Response toResponse(PersistenceException exception) {
        final String METHOD_NAME = "toResponse";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, exception.toString());

        LOGGER.fine(String.format("Cause: %s", exception.getCause().toString()));

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, null);
        return null;
    }
}
