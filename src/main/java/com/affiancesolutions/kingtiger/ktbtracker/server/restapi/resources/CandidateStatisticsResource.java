package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.Candidate;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.JournalPost;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.CandidatesDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.JournalPostsDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.TrackingDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorResult;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorStatus;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.FullStatisticsResult;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.Statistics;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.*;

@SimplyTimed
@RequestScoped
public class CandidateStatisticsResource {

    private static final String CLASS_NAME = CandidateStatisticsResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @PathParam(PARAM_CAN_ID)
    private int candidateId;

    @Inject
    private CandidatesDAO candidatesDAO;

    @Inject
    private TrackingDAO trackingDAO;

    @Inject
    private JournalPostsDAO journalPostsDAO;

    private Candidate candidate;

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    /**
     * @param fromDate
     * @param toDate
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatistics(
            @QueryParam(PARAM_FROM_DATE) String fromDate,
            @QueryParam(PARAM_TO_DATE) String toDate
    ) {
        final String METHOD_NAME = "getStatistics";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidateId, fromDate, toDate});
        Candidate candidate = candidatesDAO.find(candidateId);

        LocalDate startDate = candidate.getCycle().getCycleStart();
        LocalDate endDate = candidate.getCycle().getCycleEnd();

        if (fromDate != null || toDate != null) {
            if (fromDate != null) {
                try {
                    startDate = LocalDate.parse(fromDate);
                } catch (DateTimeParseException e) { //NOSONAR
                }
            }

            if (toDate != null) {
                try {
                    endDate = LocalDate.parse(toDate);
                } catch (DateTimeParseException e) { //NOSONAR
                }
            }
        } else {
            if (fromDate == null && candidate.getCycle().getCyclePreStart() != null) {
                startDate = candidate.getCycle().getCyclePreStart();
            }

            if (toDate == null && candidate.getCycle().getCyclePostEnd() != null) {
                endDate = candidate.getCycle().getCyclePostEnd();
            }
        }

        Long journalCount = journalPostsDAO.countDaysJournalByCandidate(candidate, startDate, endDate);
        LOGGER.finest(String.format("Journal count: %d", journalCount));
        List<LocalDate> journalDays = journalPostsDAO.findDaysJournalByCandidate(candidate, startDate, endDate);
        LOGGER.finest(String.format("Journal days: %s", Arrays.toString(journalDays.toArray(new LocalDate[0]))));
        List<JournalPost> journalPosts = journalPostsDAO.findRangeByCandidate(candidate, startDate, endDate);
        LOGGER.finest(String.format("Journal posts: %s", Arrays.toString(journalPosts.toArray(new JournalPost[0]))));

        Statistics statistics = trackingDAO.getStatistics(candidate, startDate, endDate);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, statistics);
        return Response.ok(statistics).build();
    }

    @GET
    @Path("full")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFullStatistics(
            @DefaultValue(SCOPE_ALL) @QueryParam(PARAM_SCOPE) String scope
    ) {
        final String METHOD_NAME = "getFullStatistics";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{candidate, scope});
        FullStatisticsResult result = new FullStatisticsResult();

        //
        // If the candidate being tracked does not exist, return 404 Not Found
        //
        if (candidate == null) {
            throw new NotFoundException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("Candidate %s could not be found.", candidateId
                            )))).build());
        }

        result.setCandidateId(candidate.getId());
        result.setCycleId(candidate.getCycle().getId());

        double weekFactor = 7.0 / (double)  candidate.getCycle().getCycleStart()
                .until(candidate.getCycle().getCycleEnd().plusDays(1), ChronoUnit.DAYS);

        candidate.getCycle().getCycleStart().datesUntil(candidate.getCycle().getCycleEnd(), Period.ofWeeks(1))
                .map(weekStartDate -> trackingDAO.getStatistics(candidate, weekStartDate, weekStartDate.plusDays(6)))
                .forEach(weekStats -> {
                    if (!scope.equalsIgnoreCase(SCOPE_ALL)) {
                        if (scope.equalsIgnoreCase(SCOPE_PHYSICAL)) {
                            weekStats.setPhysical(candidate.getCycle().getRequirements(), weekFactor);
                        }
                        if (scope.equalsIgnoreCase(SCOPE_CLASSES)) {
                            weekStats.setClasses(candidate.getCycle().getRequirements(), weekFactor);
                        }
                        if (scope.equalsIgnoreCase(SCOPE_OTHER)) {
                            weekStats.setOther(candidate.getCycle().getRequirements(), weekFactor);
                        }
                    }
            result.getWeekly().add(weekStats);/*
            result.getCycle().setMiles(result.getCycle().getMiles() + weekStats.getMiles());
            result.getCycle().setPushUps(result.getCycle().getPushUps() + weekStats.getPushUps());
            result.getCycle().setSitUps(result.getCycle().getSitUps() + weekStats.getSitUps());
            result.getCycle().setBurpees(result.getCycle().getBurpees() + weekStats.getBurpees());
            result.getCycle().setKicks(result.getCycle().getKicks() + weekStats.getKicks());
            result.getCycle().setPoomsae(result.getCycle().getPoomsae() + weekStats.getPoomsae());
            result.getCycle().setSelfDefense(result.getCycle().getSelfDefense() + weekStats.getSelfDefense());
            result.getCycle().setSparring(result.getCycle().getSparring() + weekStats.getSparring());
            result.getCycle().setJumps(result.getCycle().getPushUps() + weekStats.getJumps());
            result.getCycle().setPullUps(result.getCycle().getPullUps() + weekStats.getPullUps());
            result.getCycle().setPlanks(result.getCycle().getPlanks() + weekStats.getPlanks());
            result.getCycle().setRollsFalls(result.getCycle().getRollsFalls() + weekStats.getRollsFalls());
            result.getCycle().setClassSaturday(result.getCycle().getClassSaturday() + weekStats.getClassSaturday());
            result.getCycle().setClassWeekday(result.getCycle().getClassWeekday() + weekStats.getClassWeekday());
            result.getCycle().setClassPMAA(result.getCycle().getClassPMAA() + weekStats.getClassPMAA());
            result.getCycle().setClassSparring(result.getCycle().getClassSparring() + weekStats.getClassSparring());
            result.getCycle().setClassMasterQ(result.getCycle().getClassMasterQ() + weekStats.getClassMasterQ());
            result.getCycle().setClassDreamTeam(result.getCycle().getClassDreamTeam() + weekStats.getClassDreamTeam());
            result.getCycle().setClassHyperPro(result.getCycle().getClassHyperPro() + weekStats.getClassHyperPro());
            result.getCycle().setMeditation(result.getCycle().getMeditation() + weekStats.getMeditation());
            result.getCycle().setRandomActs(result.getCycle().getRandomActs() + weekStats.getRandomActs());
            result.getCycle().setMentor(result.getCycle().getMentor() + weekStats.getMentor());
            result.getCycle().setMentee(result.getCycle().getMentee() + weekStats.getMentee());
            result.getCycle().setLeadership(result.getCycle().getLeadership() + weekStats.getLeadership());
            result.getCycle().setLeadership2(result.getCycle().getLeadership2() + weekStats.getLeadership2());
            result.getCycle().setJournals(result.getCycle().getJournals() + weekStats.getJournals());*/
        });
        result.setCycle(trackingDAO.getStatistics(candidate, candidate.getCycle().getCycleStart(),
                candidate.getCycle().getCycleEnd()));
        if (!scope.equalsIgnoreCase(SCOPE_ALL)) {
            if (scope.equalsIgnoreCase(SCOPE_PHYSICAL)) {
                result.getCycle().setPhysical(candidate.getCycle().getRequirements(), 1.0);
            }
            if (scope.equalsIgnoreCase(SCOPE_CLASSES)) {
                result.getCycle().setClasses(candidate.getCycle().getRequirements(), 1.0);
            }
            if (scope.equalsIgnoreCase(SCOPE_OTHER)) {
                result.getCycle().setOther(candidate.getCycle().getRequirements(), 1.0);
            }
        }
        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return Response.ok(result).build();
    }
}
