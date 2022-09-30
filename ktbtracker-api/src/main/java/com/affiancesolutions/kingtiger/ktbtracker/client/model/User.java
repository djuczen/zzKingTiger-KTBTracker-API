package com.affiancesolutions.kingtiger.ktbtracker.client.model;

import com.affiancesolutions.kingtiger.ktbtracker.client.model.UserGroup;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class User implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = 4994649880431000041L;

    /**
     * The unique user ID provided by the Identity Provider (IdP).
     */
    private String id;

    /**
     * The display name of the user provided by the Identity Provider (IdP).
     */
    private String displayName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return String.format("User(id=%s, displayName='%s')", id, displayName);
    }
}
