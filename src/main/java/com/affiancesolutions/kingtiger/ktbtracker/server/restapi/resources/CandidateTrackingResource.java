package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.Tracking;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CandidatesDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CyclesDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.JournalPostsDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.TrackingDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Logger;

import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.*;

/**
 * The sub-resource class for managing tracking data for a particular candidate.
 *
 */
@SimplyTimed
@RequestScoped
/**
 * This sub-resource
 */
public class CandidateTrackingResource {

    private static final String CLASS_NAME = CandidateTrackingResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private UriInfo uriInfo;

    @PathParam(PARAM_CYCLE_ID)
    private String cycleId;

    @PathParam(PARAM_CAN_ID)
    private String candidateId;

    @Inject
    private CyclesDAO cyclesDAO;

    @Inject
    private CandidatesDAO candidatesDAO;

    @Inject
    private TrackingDAO trackingDAO;

    @Inject
    private JournalPostsDAO journalPostsDAO;

    @Inject
    private JsonWebToken jsonWebToken;

    private Candidate candidate;


    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }


    /**
     * Retrieves a set of one or more tracking records and statistics for the given candidate and date range.
     *
     * <p>
     *     Retrieves one or more tracking records for the specified candidate. If the starting date ("from_date") is
     *     omitted it will default to the current date. If the ending date ("to_date") is omitted it will default to
     *     the starting date.
     * </p>
     * <p>
     *     The range can also be specified by the relative week ("week") number (1-based) of the cycle the candidate is
     *     tracking. Specifying a week number overrides any date range provided.
     * </p>
     * <pre>
     *      GET /api/cycles/&lt;cycle_id&gt;/candidates/&lt;can_id&gt;/tracking
     * </pre>
     *
     * @param fromDate the starting date in yyyy-MM-dd format, or the current date if omitted
     * @param toDate the ending date in yyyy-MM-dd format, or the current date if omitted
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCandidateTracking(
            @QueryParam(PARAM_FROM_DATE) String fromDate,
            @QueryParam(PARAM_TO_DATE) String toDate,
            @QueryParam(PARAM_WEEK) int cycleWeek
    ) {
        final String METHOD_NAME = "getCandidateTracking";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidate, fromDate, toDate});
        CandidateTrackingResult candidateTrackingResult = new CandidateTrackingResult();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();

        //
        // If the candidate being tracked does not exist, return 404 Not Found
        //
        if (candidate == null) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Candidate %s could not be found.", candidateId
                            )))).build());
        }

        //
        // If a non-zero week number is specifed, use it to calculate the date range. Otherwise, use the date
        // range provided or default to today.
        //
        if (cycleWeek > 0) {
            startDate = candidate.getCycle().getCycleStart().plusWeeks(cycleWeek - 1);
            endDate = startDate.plusDays(6);

            if (startDate.isAfter(candidate.getCycle().getCycleEnd())) {
                throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResult(new ErrorStatus(Response.Status.BAD_REQUEST.getStatusCode(),
                                String.format("Week %d is not within the specified cycle.", cycleWeek
                                )))).build());
            }
        } else {
            if (fromDate != null) {
                try {
                    startDate = LocalDate.parse(fromDate);
                    endDate = startDate;
                } catch (DateTimeParseException e) { //NOSONAR
                }
            }

            if (toDate != null) {
                try {
                    endDate = LocalDate.parse(toDate);
                } catch (DateTimeParseException e) { //NOSONAR
                }
            }

            if (startDate.isAfter(candidate.getCycle().getCycleEnd())) {
                throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResult(new ErrorStatus(Response.Status.BAD_REQUEST.getStatusCode(),
                                String.format("Date range (%s - %s) is not within the specified cycle.", startDate,
                                        endDate)))).build());
            }
        }


        candidateTrackingResult.setUserId(candidate.getUser().getId());
        candidateTrackingResult.setCandidateId(candidate.getId());
        candidateTrackingResult.setCycleId(candidate.getCycle().getId());
        candidateTrackingResult.setStartDate(startDate);
        candidateTrackingResult.setEndDate(endDate);

        candidateTrackingResult.setStatistics(trackingDAO.getStatistics(candidate, startDate, endDate));

        // Generate Tracking records for the inclusive days (if any)
        startDate.datesUntil(endDate).forEach(date -> {
            LOGGER.finest(String.format("Generating empty %s...", date));
            candidateTrackingResult.getDaily().add(new TrackingResult(candidate, date));
        });
        // Always generate the ending date, to make it inclusive
        candidateTrackingResult.getDaily().add(new TrackingResult(candidate, endDate));
        LOGGER.finest(String.format("Generating empty %s...", endDate));

        LOGGER.finest("Populating with tracking data...");
        List<Tracking> trackingData = trackingDAO.findRangeByCandidate(candidate, startDate, endDate);
        candidateTrackingResult.getDaily().forEach(trackingResult -> {
            trackingData.stream()
                    .filter(tracking -> tracking.getTrackingDate().equals(trackingResult.getTrackingDate()))
                    .findFirst()
                    .ifPresent(tracking -> {
                        LOGGER.finest(String.format("Injecting tracking data for %s...", tracking.getTrackingDate()));
                        trackingResult.setRequirements(tracking.getRequirements());
                    });
        });

        LOGGER.finest("Populating with journaling data...");
        List<LocalDate> journalDays = journalPostsDAO.findDaysJournalByCandidate(candidate, startDate, endDate);
        candidateTrackingResult.getDaily().forEach(trackingResult -> {
            journalDays.stream()
                    .filter(journalDate -> journalDate.equals(trackingResult.getTrackingDate()))
                    .findFirst()
                    .ifPresent(journalDate -> {
                        LOGGER.finest(String.format("Injecting journaling data for %s...", journalDate));
                        trackingResult.getRequirements().setJournals(1);
                    });
        });

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, candidateTrackingResult);
        return Response.ok(candidateTrackingResult)
                .header(HEADER_API_QUERY_COUNT, candidateTrackingResult.getDaily().size())
                .build();
    }

    /**
     * Retrieves a set of one or more tracking records and statistics for the given candidate and date range.
     *
     * <p>
     *     Retrieves one or more tracking records for the specified candidate identifier.
     * </p>
     * <pre>
     *      PUT /api/candidates/&lt;can_id&gt;/tracking/&lt;tracking_id&gt;
     *      PUT /api/cycles/&lt;cycle_id&gt;/candidates/&lt;can_id&gt;/tracking/&lt;tracking_id&gt;
     * </pre>
     *
     * @return
     */
    @PUT
    @Path("{tracking_date:20[0-9]{2}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(ALL_AUTHENTICATED)
    public Response updateCandidateTracking(
            @PathParam(PARAM_TRACKING_DATE) String trackingDate,
            TrackingRequest trackingRequest
    ) {
        final String METHOD_NAME = "updateCandidateTracking";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidate, trackingDate, trackingRequest});
        Response response;
        Tracking tracking;

        LocalDate normalizedDate = LocalDate.now();
        if (trackingDate != null) {
            try {
                normalizedDate = LocalDate.parse(trackingDate);
            } catch (DateTimeParseException e) { //NOSONAR
                throw new BadRequestException(Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResult(new ErrorStatus(Response.Status.BAD_REQUEST.getStatusCode(),
                                String.format("Tracking date [%s] is not valid.", trackingDate
                                )))).build());
            }
        }

        //
        // If the candidate being tracked does not exist, return 404 Not Found
        //
        if (candidate == null) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Candidate %s could not be found.", candidateId
                            )))).build());
        }

        tracking = trackingDAO.findByCandidate(candidate, normalizedDate);

        if (tracking != null) {
            tracking.setCycle(candidate.getCycle());
            tracking.setCandidate(candidate);
            tracking.setRequirements(trackingRequest.getRequirements());

            TrackingResult trackingResult = new TrackingResult(trackingDAO.update(tracking));

            response = Response.ok(trackingResult).build();
        } else {
            tracking = new Tracking();
            tracking.setCycle(candidate.getCycle());
            tracking.setCandidate(candidate);
            tracking.setTrackingDate(normalizedDate);
            tracking.setRequirements(trackingRequest.getRequirements());

            TrackingResult trackingResult = new TrackingResult(trackingDAO.create(tracking));

            URI createdLocation = uriInfo.getAbsolutePathBuilder().build();

            response = Response.created(createdLocation).entity(trackingResult).build();
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, response);
        return response;
    }
}
