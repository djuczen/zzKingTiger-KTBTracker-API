package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.User;
import com.affiancesolutions.kingtiger.ktbtracker.server.utils.PersonNameComponents;

import java.io.Serializable;

public class UserResult implements Serializable {

    private static final long serialVersionUID = -3098682404236350385L;

    private PersonNameComponents personNameComponents;

    private String id;

    private String displayName;

    public UserResult() {}

    public UserResult(User user) {
        this.id = user.getId();
        this.displayName = user.getDisplayName();
        this.personNameComponents = new PersonNameComponents(user.getDisplayName());
    }
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

    public String getGivenName() {
        return personNameComponents.getGivenName();
    }
    public String getFullName() {
        return personNameComponents.familyNameSequence();
    }

    @Override
    public String toString() {
        return String.format("UserResult(fullName=%s)", getFullName());
    }
}
