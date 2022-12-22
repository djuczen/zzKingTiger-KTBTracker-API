package com.affiancesolutions.kingtiger.ktbtracker.server.model.jpa;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.Cycle;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.Metadata;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@RequestScoped
public class MetadataListener {

    private static final String CLASS_NAME = MetadataListener.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private SecurityContext securityContext;

    @PrePersist
    public void onCreate(Object entity) {
        final String METHOD_NAME = "prePersist";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, entity);

        // We are only interested in the embeddable Metadata entity
        if (entity instanceof Metadata) {
            Metadata metadata = (Metadata) entity;

            // Set the "created" (persist) timestamp of the entity
            metadata.setCreated(LocalDateTime.now());

            // If the security context is available also set the "created by" for the entity
            if (securityContext != null) {
                metadata.setCreatedBy(securityContext.getUserPrincipal().getName());
            }
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    @PreUpdate
    public void onUpdate(Object entity) {
        final String METHOD_NAME = "onUpdate";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, entity);

        // We are only interested in the embeddable Metadata entity
        if (entity instanceof Metadata) {
            Metadata metadata = (Metadata) entity;

            // Update the "modified" (update) timestamp of the entity
            metadata.setModified(LocalDateTime.now());

            // If the security context is available also set the "modified by" for the entity
            if (securityContext != null) {
                metadata.setModifiedBy(securityContext.getUserPrincipal().getName());
            }
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }
}
