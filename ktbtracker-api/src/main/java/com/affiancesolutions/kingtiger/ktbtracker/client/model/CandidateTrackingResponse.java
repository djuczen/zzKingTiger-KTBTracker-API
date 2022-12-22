package com.affiancesolutions.kingtiger.ktbtracker.client.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CandidateTrackingResponse implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = -276837339338515716L;

    private int candidateId;

    private int cycleId;

    private LocalDate startDate;

    private LocalDate endDate;

    StatisticsResponse statistics = new StatisticsResponse();

    List<TrackingResponse> daily = new ArrayList<>();
}
