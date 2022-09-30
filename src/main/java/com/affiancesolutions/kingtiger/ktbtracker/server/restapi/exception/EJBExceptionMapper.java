package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.exception;

import jakarta.ejb.EJBException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Logger;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {

    private static final String CLASS_NAME = EJBExceptionMapper.class.getName();

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
    public Response toResponse(EJBException exception) {
        final String METHOD_NAME = "toResponse";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, getRootCause(exception.getCausedByException()).toString());
        Throwable rootCause = getRootCause(exception.getCausedByException());
        Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(rootCause.getLocalizedMessage()).build();

        if (rootCause instanceof SQLIntegrityConstraintViolationException) {
            response = Response.status(Response.Status.CONFLICT).entity(rootCause.getLocalizedMessage()).build();
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, response);
        return response;
    }

    private Throwable getRootCause(Throwable t) {
        if (t.getCause() != null) {
            return getRootCause(t.getCause());
        }

        return t;
    }
}
