package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Cycle;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CyclesDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.CycleRequest;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorResult;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorStatus;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import static com.affiancesolutions.kingtiger.ktbtracker.client.Constants.PARAM_CYCLE_ID;
import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.*;

/**
 * The root resource class for cycles.
 */
@SimplyTimed
@RequestScoped
@RolesAllowed({ROLE_ADMIN, ROLE_CANDIDATE})
@Path("/cycles")
public class CyclesResource {

    private static final String CLASS_NAME = CyclesResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private UriInfo uriInfo;

    @Inject
    private CyclesDAO cyclesDAO;

    @Inject
    private CycleResource cycleResource;

    @Inject
    private CycleCandidatesResource cycleCandidatesResource;

    @Context
    ResourceContext resourceContext;

    @Inject
    private JsonWebToken jsonWebToken;

    /**
     * @return
     * @throws IOException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCycles() throws IOException {
        final String METHOD_NAME = "listCycles";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        LOGGER.finest(String.format("Roles: %s, displayName: %s", jsonWebToken.getGroups(), jsonWebToken.getClaim("name")));
        List<Cycle> resultList = cyclesDAO.findAll();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return Response.ok(resultList).build();
    }

    /**
     * @param cycleRequest
     * @return
     * @throws IOException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCycle(
            CycleRequest cycleRequest
    ) throws IOException {
        final String METHOD_NAME = "createCycle";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, cycleRequest);

        if (cyclesDAO.findByAlias(cycleRequest.getAlias()) != null) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.CONFLICT.getStatusCode(),
                            String.format("Cycle with title '%s' already exists", cycleRequest.getTitle()))))
                    .build());
        }

        Cycle newCycle = new Cycle();
        newCycle.setId(0);
        newCycle.setTitle(cycleRequest.getTitle());
        newCycle.setAlias(cycleRequest.getAlias());
        newCycle.setCycleStart(cycleRequest.getCycleStart());
        newCycle.setCycleEnd(cycleRequest.getCycleEnd());
        newCycle.setCyclePreStart(cycleRequest.getCyclePreStart());
        newCycle.setCyclePostEnd(cycleRequest.getCyclePostEnd());
        newCycle.setCycleWeekStart(cycleRequest.getCycleWeekStart());
        newCycle.setRequirements(cycleRequest.getRequirements());

        Cycle createdCycle = cyclesDAO.create(newCycle);

        URI createdLocation = uriInfo.getAbsolutePathBuilder().build();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, createdCycle);
        return Response.created(createdLocation).entity(createdCycle).build();
    }


    /**
     * Return an instance of the sub-resource {@code CycleResource} locator.
     *
     * @param cycleId
     * @return
     */
    @Path("{cycle_id:(\\d+|current)}")
    public CycleResource getCycleResource(
            @PathParam(PARAM_CYCLE_ID) String cycleId
    ) {
        final String METHOD_NAME = "getCycleResource";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, cycleId);

        if (cycleId.equalsIgnoreCase(PARAM_CURRENT)) {
            //
            // Ensure that if cycleId is "current" that there is one currently active, otherwise,
            // ensure a non-zero positive integer is specified.
            //
            if (cyclesDAO.findCurrent() == null) {
                throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                                String.format("No cycle is currently active.")))).build());
            }
        } else if (Integer.parseInt(cycleId) == 0) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Cycle %s could not be found.", cycleId
                            )))).build());
        }

        CycleResource resource = resourceContext.initResource(cycleResource);
        if (cycleId.equalsIgnoreCase(PARAM_CURRENT)) {
            resource.setCycle(cyclesDAO.findCurrent());
        } else {
            resource.setCycle(cyclesDAO.find(Integer.parseInt(cycleId)));
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resource);
        return resource;
    }
}

