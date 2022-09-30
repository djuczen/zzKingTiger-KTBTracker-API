package com.affiancesolutions.kingtiger.ktbtracker.client.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Metadata implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = -6851883223528485019L;

    /**
     * The timestamp of when the entity record was created.
     */
    private LocalDateTime created;

    /**
     * The user that created the entity record.
     */
    private String createdBy;

    /**
     * The timestamp of when the entity record was last modified.
     */
    private LocalDateTime modified;

    /**
     * The user that last modified the entity record.
     */
    private String modifiedBy;


    /**
     * Default no-argument constructor.
     */
    public Metadata() {

    }

    public Metadata(String createdBy) {
        this(LocalDateTime.now(), createdBy);
    }

    /**
     * Construct a {@code MetaData} object with the specified creation timestamp and creation user.
     *
     * @param created   the timestamp of when the entity record was created
     * @param createdBy the user that created the entity record
     */
    public Metadata(LocalDateTime created, String createdBy) {
        this.created = created;
        this.createdBy = createdBy;
    }

    /**
     * Gets the timestamp of when the entity record was created.
     *
     * @return the creation timestamp for the entity record
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Sets the timestamp of when the entity record was created.
     *
     * @param created the timestamp representing when the entity was created
     */
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * Gets the user that created the entity record.
     *
     * @return the user that created the entity record
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user the created the entity record.
     *
     * @param createdBy the user that created the entity record
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the timestamp of when the entity record was last modified.
     *
     * @return the timestamp representing when the entity record was last modified
     */
    public LocalDateTime getModified() {
        return modified;
    }

    /**
     * Sets the timestamp of when the entity record was last modified.
     *
     * @param modified the timestamp of when the entity record was last modified
     */
    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    /**
     * Gets the user that last modified the entity record.
     *
     * @return the user that last modified the entity record
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the user that last modified the entity record.
     *
     * @param modifiedBy the user that last modified the entity record
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
