package com.affiancesolutions.kingtiger.ktbtracker.server.model;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.jpa.MetadataListener;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@EntityListeners(MetadataListener.class)
@Embeddable
public class Metadata implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = -6851883223528485019L;

    /**
     * The timestamp of when the entity record was created.
     */
    @Column(name = "CREATED")
    @JsonbDateFormat("uuuu-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime created;

    /**
     * The user that created the entity record.
     */
    @Column(name = "CREATED_BY")
    private String createdBy;

    /**
     * The timestamp of when the entity record was last modified.
     */
    @Column(name = "MODIFIED")
    @JsonbDateFormat("uuuu-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime modified;

    /**
     * The user that last modified the entity record.
     */
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;


    @Column(name = "CHECKED_OUT")
    private String checkedOut;

    @Column(name = "CHECKED_OUT_TIME")
    @JsonbDateFormat("uuuu-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime checkedOutTime;

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

    public String getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(String checkedOut) {
        this.checkedOut = checkedOut;
    }

    public LocalDateTime getCheckedOutTime() {
        return checkedOutTime;
    }

    public void setCheckedOutTime(LocalDateTime checkedOutTime) {
        this.checkedOutTime = checkedOutTime;
    }
}
