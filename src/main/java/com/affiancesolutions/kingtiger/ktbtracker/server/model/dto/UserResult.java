package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.User;
import com.affiancesolutions.kingtiger.ktbtracker.server.utils.PersonNameComponents;
import com.google.firebase.auth.UserRecord;

import java.io.Serializable;

public class UserResult implements Serializable {

    private static final long serialVersionUID = -3098682404236350385L;

    private PersonNameComponents personNameComponents;

    private String userId;

    private String displayName;

    private String email;

    public UserResult() {}

    public UserResult(User user) {
        this.userId = user.getUserId();
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
        this.personNameComponents = new PersonNameComponents(user.getDisplayName());
    }

    public UserResult(UserRecord userRecord) {
        this.userId = userRecord.getUid();
        this.displayName = userRecord.getDisplayName();
        this.email = userRecord.getEmail();
        this.personNameComponents = new PersonNameComponents(userRecord.getDisplayName());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
