package com.affiancesolutions.kingtiger.ktbtracker.client.model;

import com.affiancesolutions.kingtiger.ktbtracker.client.model.User;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class UserGroup implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = 6635796569142561626L;

    /**
     * The auto-generated ID for this entity.
     */
    private Long id;

    /**
     * The title (or name) of the user group.
     */
    private String name;

    /**
     * The (optional) description of the user group.
     */
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Group(id=%d, name='%s')", id, name);
    }
}
