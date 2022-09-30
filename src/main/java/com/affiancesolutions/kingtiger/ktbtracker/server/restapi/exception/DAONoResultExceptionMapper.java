package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.exception;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.exception.DAONoResultException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class DAONoResultExceptionMapper implements ExceptionMapper<DAONoResultException> {

    private static final String CLASS_NAME = DAONoResultExceptionMapper.class.getName();

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
    public Response toResponse(DAONoResultException exception) {
        final String METHOD_NAME = "toResponse";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{exception.toString()});

        Response response = Response.status(Response.Status.NOT_FOUND)
                .entity(ExceptionHelper.toEntity(exception, Response.Status.NOT_FOUND.getStatusCode()))
                .build();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, response);
        return response;
    }
}
