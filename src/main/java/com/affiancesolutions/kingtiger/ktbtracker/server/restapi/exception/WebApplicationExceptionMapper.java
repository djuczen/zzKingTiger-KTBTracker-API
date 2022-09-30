package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    private static final String CLASS_NAME = WebApplicationExceptionMapper.class.getName();

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
    public Response toResponse(WebApplicationException exception) {
        final String METHOD_NAME = "toResponse";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{exception.toString(), exception.getResponse().getStatus()});

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, exception.getResponse());
        return exception.getResponse();
    }
}
