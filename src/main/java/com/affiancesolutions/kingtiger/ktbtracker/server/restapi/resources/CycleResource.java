package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.Cycle;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CyclesDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.CycleRequest;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorResult;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorStatus;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.checkerframework.checker.units.qual.C;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import static com.affiancesolutions.kingtiger.ktbtracker.client.Constants.PARAM_CYCLE_ID;
import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.PARAM_CURRENT;
import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.ROLE_ADMIN;


/**
 * The sub-resource class for individual cycles.
 */
@SimplyTimed
@RequestScoped
public class CycleResource {

    private static final String CLASS_NAME = CycleResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private ResourceContext resourceContext;

    @Context
    private UriInfo uriInfo;

    @PathParam(PARAM_CYCLE_ID)
    private String cycleId;

    @Inject
    private CyclesDAO cyclesDAO;

    @Inject
    private CycleCandidatesResource cycleCandidatesResource;

    private Cycle cycle;

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    /**
     *
     * This comes from {@code /api/cycles/current} or {@code /api/cycles/&lt;id&gt;}.
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCycle() {
        final String METHOD_NAME = "getCycle";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, cycle);

        if (cycle == null) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Cycle %s could not be found.", cycleId
                            )))).build());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, cycle);
        return Response.ok(cycle).build();
    }


    /**
     * Update (or create) a cycle.
     *
     * @param cycleRequest
     * @return
     * @throws IOException
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCycle(
            CycleRequest cycleRequest
    ) throws IOException {
        final String METHOD_NAME = "updateCycle";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{cycleRequest});
        Response response;

        if (cycle == null) {
            //
            // An existing cycle could not be found, so create it...
            //  (The cycle "id" comes from the path NOT the request)
            //
            Cycle newCycle = new Cycle();
            newCycle.setId(Integer.parseInt(cycleId));
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

            response = Response.created(createdLocation).entity(createdCycle).build();
        } else {
            //
            // An existing cycle was found, so update it...
            //  (The cycle "id" comes from the path NOT the request)
            //
            cycle.setId(Integer.parseInt(cycleId));
            cycle.setTitle(cycleRequest.getTitle());
            cycle.setAlias(cycleRequest.getAlias());
            cycle.setCycleStart(cycleRequest.getCycleStart());
            cycle.setCycleEnd(cycleRequest.getCycleEnd());
            cycle.setCyclePreStart(cycleRequest.getCyclePreStart());
            cycle.setCyclePostEnd(cycleRequest.getCyclePostEnd());
            cycle.setCycleWeekStart(cycleRequest.getCycleWeekStart());
            cycle.setRequirements(cycleRequest.getRequirements());

            Cycle updatedCycle = cyclesDAO.update(cycle);

            response = Response.ok().entity(updatedCycle).build();
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, response);
        return response;
    }

    /**
     * @return
     * @throws IOException
     */
    @DELETE
    @RolesAllowed({ROLE_ADMIN})
    public Response deleteCycle() throws IOException {
        final String METHOD_NAME = "deleteCycle";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{cycleId});
        Cycle cycle = cycleResolver();

        if (cycle == null) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Cycle %s could not be found.", cycleId
                            )))).build());
        }

        cyclesDAO.delete(cycle);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
        return Response.noContent().build();
    }

    /**
     * The sub-resource locator for cycle candidates.
     *
     * @return
     */
    @Path("candidates")
    public CycleCandidatesResource getCycleCandidatesResource() {
        final String METHOD_NAME = "getCycleCandidatesResource";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, cycleId);

        CycleCandidatesResource resource = resourceContext.initResource(cycleCandidatesResource);
        resource.setCycle(cycle);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resource);
        return resource;
    }

    /**
     * Validates the "cycle_id" path parameter and resolves it.
     *
     * @return
     */
    private Cycle cycleResolver() {
        //
        // Ensure that if cycleId is "current" that there is one currently active, otherwise,
        // ensure a non-zero positive integer is specified.
        //
        if (cycleId.equalsIgnoreCase(PARAM_CURRENT)) {
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

        if (cycleId.equalsIgnoreCase(PARAM_CURRENT)) {
            return cyclesDAO.findCurrent();
        }
        return cyclesDAO.find(Integer.parseInt(cycleId));
    }
}
