package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import com.affiancesolutions.kingtiger.ktbtracker.server.utils.PersonNameComponents;
import com.google.firebase.auth.UserRecord;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class UserRecordResult implements Serializable {

    private static final long serialVersionUID = 8671008565510345726L;

    private PersonNameComponents personNameComponents;

    private String uid;

    private String displayName;

    private String email;

    private boolean emailVerified;

    private String phoneNumber;

    private String photoUrl;

    private boolean disabled;

    private Instant tokensValidAfterTimestamp;

    private Map<String, Object> customClaims = new HashMap<>();

    private Instant createdAt;

    private Instant lastLoginAt;

    private Instant lastRefreshAt;


    public UserRecordResult() {}

    public UserRecordResult(UserRecord userRecord) {
        this.uid = userRecord.getUid();
        this.displayName = userRecord.getDisplayName();
        this.email = userRecord.getEmail();
        this.emailVerified = userRecord.isEmailVerified();
        this.phoneNumber = userRecord.getPhoneNumber();
        this.photoUrl = userRecord.getPhotoUrl();
        this.disabled = userRecord.isDisabled();
        this.tokensValidAfterTimestamp = Instant.ofEpochMilli(userRecord.getTokensValidAfterTimestamp());
        this.customClaims = userRecord.getCustomClaims();
        this.createdAt = Instant.ofEpochMilli(userRecord.getUserMetadata().getCreationTimestamp());
        this.lastLoginAt = Instant.ofEpochMilli(userRecord.getUserMetadata().getLastSignInTimestamp());
        this.lastRefreshAt = Instant.ofEpochMilli(userRecord.getUserMetadata().getLastRefreshTimestamp());

        this.personNameComponents = new PersonNameComponents(userRecord.getDisplayName());
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Instant getTokensValidAfterTimestamp() {
        return tokensValidAfterTimestamp;
    }

    public void setTokensValidAfterTimestamp(Instant tokensValidAfterTimestamp) {
        this.tokensValidAfterTimestamp = tokensValidAfterTimestamp;
    }

    public Map<String, Object> getCustomClaims() {
        return customClaims;
    }

    public void setCustomClaims(Map<String, Object> customClaims) {
        this.customClaims = customClaims;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Instant lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Instant getLastRefreshAt() {
        return lastRefreshAt;
    }

    public void setLastRefreshAt(Instant lastRefreshAt) {
        this.lastRefreshAt = lastRefreshAt;
    }

    public String getGivenName() {
        return personNameComponents.getGivenName();
    }

    public String getFullName() {
        return personNameComponents.familyNameSequence();
    }
}
