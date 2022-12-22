package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.Cycle;
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
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.*;

@SimplyTimed
@RequestScoped
@Path("/candidates")
public class CandidatesResource {

    private static final String CLASS_NAME = CandidatesResource.class.getName();

    private static Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private ResourceContext resourceContext;

    @Inject
    private CandidateResource candidateResource;

    @Inject
    private CyclesDAO cyclesDAO;

    @Inject
    private CandidatesDAO candidatesDAO;

    @Inject
    private JsonWebToken jsonWebToken;


    /**
     *
     * @param cycleId
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCandidates(
            @QueryParam(PARAM_CYCLE_ID) @DefaultValue(ZERO) String cycleId
    ) {
        final String METHOD_NAME = "listCandidates";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, cycleId);
        Comparator<CandidateResult> NameComparator = Comparator.comparing(CandidateResult::getFamilyNameOrder);
        Cycle cycle;

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
            cycle = cyclesDAO.findCurrent();
        } else {
            cycle = cyclesDAO.find(Integer.parseInt(cycleId));
        }

        List<CandidateResult> resultList = candidatesDAO.findAllByCycle(cycle)
                .stream().map(CandidateResult::new)
                .sorted(NameComparator).collect(Collectors.toList());

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return Response.ok(resultList).header(HEADER_API_QUERY_COUNT, resultList.size()).build();
    }

    /**
     *
     * @param candidateId
     * @return
     */
    @Path("{can_id:\\d+}")
    public CandidateResource getCandidateResource(
            @PathParam(PARAM_CAN_ID) String candidateId
    ) {
        final String METHOD_NAME = "getCycleCandidate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidateId});

        //
        // Ensure that if candidateId is "me" that there is one currently active, otherwise,
        // ensure a non-zero positive integer is specified.
        //
        if (Integer.parseInt(candidateId) == 0) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Candidate %s could not be found.", candidateId
                            )))).build());
        }

        CandidateResource resource = resourceContext.initResource(candidateResource);
        resource.setCandidate(candidatesDAO.find(Integer.parseInt(candidateId)));

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resource);
        return resource;
    }
}
