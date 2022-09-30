package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.exception;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.exception.DAOPersistenceException;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorStatus;
import jakarta.persistence.EntityExistsException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class DAOPersistenceExceptionMapper implements ExceptionMapper<DAOPersistenceException> {

    private static final String CLASS_NAME = DAOPersistenceExceptionMapper.class.getName();

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
    public Response toResponse(DAOPersistenceException exception) {
        final String METHOD_NAME = "toResponse";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, exception.toString());

        if (getRootCause(exception) instanceof EntityExistsException) {
            return Response.status(409).entity(new ErrorStatus(409, exception)).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorStatus(exception)).build();
    }

    private Throwable getRootCause(Throwable t) {
        if (t.getCause() != null) {
            return getRootCause(t.getCause());
        }

        return t;
    }
}
