package com.affiancesolutions.kingtiger.ktbtracker.server.model.entity;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.User;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERGROUPS", indexes = {
        @Index(name = "USERGROUP_NAME_NDX", columnList = "NAME", unique = true)
})
@NamedQueries({
        @NamedQuery(name = "UserGroup.findAll",
                query = "SELECT t FROM UserGroup t"
                        + " ORDER BY t.name"),
        @NamedQuery(name = "UserGroup.findByName",
                query = "SELECT t FROM UserGroup t"
                        + " WHERE t.name = :groupName")
})
public class UserGroup implements Serializable {

    /**
     * Versioning UID for serialization.
     */
    private static final long serialVersionUID = 6635796569142561626L;

    /**
     * The auto-generated ID for this entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    /**
     * The title (or name) of the user group.
     */
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * The (optional) description of the user group.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Object Relationship Mapping (ORM) many-to-many relationship to the users (User) that are members.
     */
    @JsonbTransient
    @ManyToMany
    @JoinTable(name = "USER_USERGROUPS",
            joinColumns = {
                    @JoinColumn(name = "USERGROUP_ID", referencedColumnName = "ID", nullable = false)
            }, inverseJoinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    })
    private Set<User> users = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Set<User> getUsers() {
        return users;
    }

    /**
     * Add a user ({@link User}) to the user group membership, ensuring that both sides of the ORM relationship are updated.
     *
     * @param user
     */
    public void addUser(User user) {
        if (!getUsers().contains(user)) {
            getUsers().add(user);
        }
        if (!user.getGroups().contains(this)) {
            user.getGroups().add(this);
        }
    }

    /**
     * Remove a user ({@link User}) from the user group membership, ensuring that both sides of the ORM relationship are updated.
     *
     * @param user
     */
    public void removeUser(User user) {
        if (getUsers().contains(user)) {
            getUsers().remove(user);
        }
        if (user.getGroups().contains(this)) {
            user.getGroups().remove(this);
        }
    }

    @Override
    public String toString() {
        return String.format("Group(id=%d, name='%s')", id, name);
    }
}
