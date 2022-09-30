package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.Constants;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.Cycle;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CyclesDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorResult;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorStatus;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@SimplyTimed
@RequestScoped
@RolesAllowed(Constants.ALL_AUTHENTICATED)
@Path("/cycles")
public class CyclesResource {

    private static final String CLASS_NAME = CyclesResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private UriInfo uriInfo;

    @Inject
    private CyclesDAO cyclesDAO;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCycles() throws IOException {
        final String METHOD_NAME = "listCycles";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        List<Cycle> resultList = cyclesDAO.findAll();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return Response.ok(resultList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCycle(Cycle cycle) throws IOException {
        final String METHOD_NAME = "createCycle";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, cycle);

        if (cyclesDAO.findByAlias(cycle.getAlias()) != null) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.CONFLICT.getStatusCode(),
                            String.format("Cycle with title '%s' already exists", cycle.getTitle()))))
                    .build());
        }

        // Ensure that the ID is zero.
        cycle.setId(0L);
        Cycle result = cyclesDAO.create(cycle);
        URI location = uriInfo.getAbsolutePathBuilder()
                        .path(cycle.getId().toString())
                                .build();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return Response.created(location).entity(result).build();
    }

    @GET
    @Path("current")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentCycle() throws IOException {
        final String METHOD_NAME = "getCurrentCycle";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        Cycle result = cyclesDAO.findCurrent();

        // If the result is null, return 404 Not Found
        if (result == null) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("No cycle is currently active.")))).build());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return Response.ok(result).build();
    }

    @GET
    @Path("{cycleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCycle(
            @PathParam(Constants.PARAM_CYCLE_ID) @DefaultValue(Constants.ZERO) Long cycleId
    ) throws IOException {
        final String METHOD_NAME = "getCycle";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{cycleId});

        Cycle result = cyclesDAO.find(cycleId);

        // If the result is null, return 404 Not Found
        if (result == null) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Cycle %d could not be found.", cycleId
                            )))).build());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return Response.ok(result).build();
    }

    @PUT
    @Path("{cycleId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCycle(
            @PathParam(Constants.PARAM_CYCLE_ID) @DefaultValue(Constants.ZERO) Long cycleId,
            Cycle cycle
    ) throws IOException {
        final String METHOD_NAME = "updateCycle";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[] { cycleId, cycle });
        Cycle result = null;

        if (cyclesDAO.find(cycleId) == null) {
            // Ensure the entity ID is not set
            cycle.setId(0L);
            result = cyclesDAO.create(cycle);
        } else {
            // Ensure the entity ID is correct
            cycle.setId(cycleId);
            result = cyclesDAO.update(cycle);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return Response.ok(result).build();
    }

    @DELETE
    @Path("{cycleId}")
    public Response deleteCycle(
            @PathParam(Constants.PARAM_CYCLE_ID) @DefaultValue(Constants.ZERO) Long cycleId
    ) throws IOException {
        final String METHOD_NAME = "deleteCycle";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[] { cycleId });

        Cycle cycle = cyclesDAO.find(cycleId);
        if (cycle == null) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Cycle %d could not be found.", cycleId
                                    )))).build());
        }

        cyclesDAO.delete(cycle);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
        return Response.noContent().build();
    }
}
