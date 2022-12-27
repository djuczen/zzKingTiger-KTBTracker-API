package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CandidatesDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CyclesDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.UsersDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.CandidateRequest;
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
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import java.net.URI;
import java.util.logging.Logger;

import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.PARAM_CAN_ID;

/**
 * The sub-resource class for managing individual candidates.
 *
 */
@SimplyTimed
@RequestScoped
public class CandidateResource {

    private static final String CLASS_NAME = CandidatesResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private ResourceContext resourceContext;

    @Context
    private UriInfo uriInfo;

    @PathParam(PARAM_CAN_ID)
    private String candidateId;

    @Inject
    private CyclesDAO cyclesDAO;

    @Inject
    private CandidatesDAO candidatesDAO;

    @Inject
    private UsersDAO usersDAO;

    @Inject
    private CandidateTrackingResource candidateTrackingResource;

    @Inject
    private CandidateStatisticsResource candidateStatisticsResource;

    private Candidate candidate;


    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCandidate() {
        final String METHOD_NAME = "getCandidate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidate});

        //
        // If the candidate does not exist, return 404 Not Found
        //
        if (candidate == null) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Candidate %s could not be found.", candidateId
                            )))).build());
        }

        CandidateResult result = new CandidateResult(candidate);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return Response.ok(result).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCandidate(
            CandidateRequest candidateRequest
    ) {
        final String METHOD_NAME = "updateCandidate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidateRequest});
        Response response;

        if (candidate == null) {
            Candidate newCandidate = new Candidate();
            newCandidate.setId(Integer.parseInt(candidateId));
            newCandidate.setCycle(cyclesDAO.find(candidateRequest.getCycleId()));
            newCandidate.setUser(usersDAO.find(candidateRequest.getUserId()));

            Candidate createdCandidate = candidatesDAO.create(newCandidate);

            URI createdLocation = uriInfo.getAbsolutePathBuilder().build();

            response = Response.created(createdLocation)
                    .entity(createdCandidate)
                    .build();
        } else {
            Candidate updateCandidate = new Candidate();
            updateCandidate.setId(candidate.getId());
            updateCandidate.setCycle(candidate.getCycle());
            updateCandidate.setUser(usersDAO.find(candidateRequest.getUserId()));

            Candidate updatedCandidate = candidatesDAO.update(updateCandidate);

            response = Response.ok(updatedCandidate).build();
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, response);
        return response;
    }

    @Path("tracking")
    public CandidateTrackingResource getCandidateTrackingResource() {
        final String METHOD_NAME = "getCandidateTrackingResource";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidateId});

        //
        // If the candidate does not exist, return 404 Not Found
        //
        if (candidate == null) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Candidate %s could not be found.", candidateId
                            )))).build());
        }

        CandidateTrackingResource resource = resourceContext.initResource(candidateTrackingResource);
        resource.setCandidate(candidate);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resource);
        return resource;
    }

    @Path("statistics")
    public CandidateStatisticsResource getCandidateStatisticsResource() {
        final String METHOD_NAME = "getCandidateStatisticsResource";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidateId});

        //
        // If the candidate does not exist, return 404 Not Found
        //
        if (candidate == null) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Candidate %s could not be found.", candidateId
                            )))).build());
        }

        CandidateStatisticsResource resource = resourceContext.initResource(candidateStatisticsResource);
        resource.setCandidate(candidate);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resource);
        return resource;
    }
}
