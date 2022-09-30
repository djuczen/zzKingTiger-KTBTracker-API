package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.exception;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.exception.DAOEntityExistsException;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorStatus;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class DAOEntityExistsExceptionMapper implements ExceptionMapper<DAOEntityExistsException> {

    private static final String CLASS_NAME = DAOEntityExistsExceptionMapper.class.getName();

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
    public Response toResponse(DAOEntityExistsException exception) {
        final String METHOD_NAME = "toRespone";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, exception.toString());

        Response response = Response.status(409).entity(new ErrorStatus(409, exception)).build();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, response);
        return response;
    }
}
