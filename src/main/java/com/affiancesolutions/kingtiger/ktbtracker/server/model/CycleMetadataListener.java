package com.affiancesolutions.kingtiger.ktbtracker.server.model;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.PrePersist;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.util.logging.Logger;

@RequestScoped
public class CycleMetadataListener {

    private static final String CLASS_NAME = CycleMetadataListener.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private SecurityContext securityContext;

    @PrePersist
    public void prePersist(Cycle cycle) {
        final String METHOD_NAME = "prePersist";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, cycle);

        LOGGER.fine(String.format("Cycle: %s, SecurityContext: %s", cycle, securityContext));

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }
}
