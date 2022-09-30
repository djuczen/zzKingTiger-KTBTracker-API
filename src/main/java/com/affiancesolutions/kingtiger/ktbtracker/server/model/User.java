package com.affiancesolutions.kingtiger.ktbtracker.server.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
@NamedQueries({
        @NamedQuery(name = "User.findAll",
                query = "SELECT t FROM User t"
                        + " ORDER BY t.displayName"),
        @NamedQuery(name = "User.findByName",
                query = "SELECT t FROM User t"
                        + " WHERE t.displayName = :displayName")
})
public class User implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = 4994649880431000041L;

    /**
     * The unique user ID provided by the Identity Provider (IdP).
     */
    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    /**
     * The display name of the user provided by the Identity Provider (IdP).
     */
    @Column(name = "DISPLAY_NAME")
    private String displayName;

    /**
     * Object Relationship Mapping (ORM) many-to-many relationship to the user groups (UserGroup) the user is a member.
     */
    @JsonbTransient
    @ManyToMany(mappedBy = "users")
    private Set<UserGroup> groups = new HashSet<>();

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

    public Set<UserGroup> getGroups() {
        return groups;
    }

    public void addGroup(UserGroup userGroup) {
        if (!getGroups().contains(userGroup)) {
            getGroups().add(userGroup);
        }
        if (!userGroup.getUsers().contains(this)) {
            userGroup.getUsers().add(this);
        }
    }

    public void removeGroup(UserGroup userGroup) {
        if (getGroups().contains(userGroup)) {
            getGroups().remove(userGroup);
        }
        if (userGroup.getUsers().contains(this)) {
            userGroup.getUsers().remove(this);
        }
    }

    @Override
    public String toString() {
        return String.format("User(id=%s, displayName='%s')", id, displayName);
    }
}
