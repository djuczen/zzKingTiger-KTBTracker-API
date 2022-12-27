package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Cycle;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CandidatesDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CyclesDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.CandidateResult;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorResult;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorStatus;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.affiancesolutions.kingtiger.ktbtracker.client.Constants.PARAM_CAN_ID;
import static com.affiancesolutions.kingtiger.ktbtracker.client.Constants.PARAM_CYCLE_ID;
import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.*;

/**
 * The sub-resource class for candidates of a particular cycle.
 *
 */
@SimplyTimed
@RequestScoped
public class CycleCandidatesResource {

    private static final String CLASS_NAME = CycleCandidatesResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private ResourceContext resourceContext;

    @PathParam(PARAM_CYCLE_ID)
    private String cycleId;

    @Inject
    private CandidateResource candidateResource;

    @Inject
    private CyclesDAO cyclesDAO;

    @Inject
    private CandidatesDAO candidatesDAO;

    @Inject
    private CandidateTrackingResource candidateTrackingResource;

    @Inject
    private CandidateStatisticsResource candidateStatisticsResource;

    @Context
    private SecurityContext securityContext;

    @Inject
    private JsonWebToken jsonWebToken;


    private Cycle cycle;

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    /**
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCycleCandidates() {
        final String METHOD_NAME = "listCycleCandidates";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, cycleId);


        if (cycle == null) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Cycle %s could not be found.", cycleId
                            )))).build());
        }

        List<CandidateResult> resultList = candidatesDAO.findAllByCycle(cycle)
                .stream().map(CandidateResult::new).collect(Collectors.toList());

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return Response.ok(resultList).header(HEADER_API_QUERY_COUNT, resultList.size()).build();
    }

    /**
     * Sub-resource locator for a specific candidate on this cycle.
     *
     * @param candidateId
     * @return
     */
    @Path("{can_id:(\\d+|me)}")
    public CandidateResource getCandidateResource(
            @PathParam(PARAM_CAN_ID) String candidateId
    ) {
        final String METHOD_NAME = "getCandidateResource";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{cycleId, candidateId, jsonWebToken});
        Candidate candidate = null;

        if (candidateId.equalsIgnoreCase(PARAM_ME)) {
            //
            // Ensure that if candidateId is "me" that there is a currently active user, otherwise,
            // ensure a non-zero positive integer is specified.
            //
            if (cycle == null) {
                throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                                String.format("No cycle is available to resolve \"me\".")))).build());
            }
            if (jsonWebToken == null) {
                throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                                String.format("No JWT is available to resolve \"me\".")))).build());
            }
        } else if (Integer.parseInt(candidateId) == 0) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Candidate %s could not be found.", candidateId
                            )))).build());
        }

        CandidateResource resource = resourceContext.initResource(candidateResource);
        if (candidateId.equalsIgnoreCase(PARAM_ME)) {
            candidateResource.setCandidate(candidatesDAO.findForUserByCycle(cycle, jsonWebToken.getSubject()));
        } else {
            candidateResource.setCandidate(candidatesDAO.find(Integer.parseInt(candidateId)));
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resource);
        return resource;
    }
}
