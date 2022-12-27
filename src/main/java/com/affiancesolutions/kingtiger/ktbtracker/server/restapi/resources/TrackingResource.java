package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Tracking;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CandidatesDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CyclesDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.TrackingDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorResult;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorStatus;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.TrackingRequest;
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
import java.util.List;
import java.util.logging.Logger;

import static com.affiancesolutions.kingtiger.ktbtracker.client.Constants.PARAM_CAN_ID;
import static com.affiancesolutions.kingtiger.ktbtracker.client.Constants.ZERO;
import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.HEADER_API_QUERY_COUNT;

@SimplyTimed
@RequestScoped
@Path("/tracking")
public class TrackingResource {

    private static final String CLASS_NAME = TrackingResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private UriInfo uriInfo;

    @Context
    private ResourceContext resourceContext;

    @Inject
    private CandidateTrackingResource candidateTrackingResource;

    @Inject
    private CyclesDAO cyclesDAO;

    @Inject
    private CandidatesDAO candidatesDAO;

    @Inject
    private TrackingDAO trackingDAO;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTracking(
            @DefaultValue(ZERO) @QueryParam(PARAM_CAN_ID) int candidateId
    ) {
        final String METHOD_NAME = "listTracking";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidateId});

        if (candidateId == 0) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Candidate %s could not be found.", candidateId
                            )))).build());
        }

        Candidate candidate = candidatesDAO.find(candidateId);

        List<Tracking> resultList = trackingDAO.findAllByCandidate(candidate);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return Response.ok().entity(resultList)
                .header(HEADER_API_QUERY_COUNT, resultList.size())
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCandidateTracking(
            TrackingRequest trackingRequest
    ) {
        final String METHOD_NAME = "createCandidateTracking";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{trackingRequest});

        Tracking tracking = new Tracking();
        tracking.setCycle(cyclesDAO.find(trackingRequest.getCycleId()));
        tracking.setCandidate(candidatesDAO.find(trackingRequest.getCandidateId()));
        tracking.setTrackingDate(trackingRequest.getTrackingDate());
        tracking.setRequirements(trackingRequest.getRequirements());

        Tracking result = trackingDAO.create(tracking);
        URI resultLocation = uriInfo.getAbsolutePathBuilder()
                .path("{can_id}")
                        .build(tracking.getCandidate().getId());

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return Response.created(resultLocation).entity(result).build();
    }

    /**
     * Return the sub-resource locator using the provided candidate identifier.
     *
     * <p>
     *     Since we do not know the tracking cycle from this resource path, the provided candidate identifier must
     *     be the physical non-zero integer candidate identifier assigned to the candidate for a cycle.
     * </p>
     * @param candidateId the unique non-zero integer identifier assigned to a candidate for a particular cycle
     * @return
     */
    @Path("{can_id:(\\d+)}")
    public CandidateTrackingResource getCandidateTrackingResource(
            @PathParam(PARAM_CAN_ID) String candidateId
    ) {
        final String METHOD_NAME = "getCandidateTrackingResource";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, candidateId);

        // Ensure that the candidate identifier is non-zero
        if (Integer.parseInt(candidateId) == 0) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Candidate %s could not be found.", candidateId
                            )))).build());
        }

        // Initialize the sub-resource locator with provided candidate
        CandidateTrackingResource resource = resourceContext.initResource(candidateTrackingResource);
        resource.setCandidate(candidatesDAO.find(Integer.parseInt(candidateId)));

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resource);
        return resource;
    }
}
