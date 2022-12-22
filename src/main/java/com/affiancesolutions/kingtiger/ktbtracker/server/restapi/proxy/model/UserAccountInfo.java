package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.model;

import java.util.ArrayList;
import java.util.List;

public class UserAccountInfo {

    private static final long serialVersionUID = -8393785861635106703L;

    private String localId;

    private String email;

    private boolean emailVerified;

    private boolean disabled;

    private int createdAt;

    private int validSince;

    private int lastLoginAt;

    private int lastRefreshAt;

    private int passwordUpdatedAt;

    private String passwordHash;

    private String customAttributes;

    private List<ProviderUserInfo> providerUserInfo = new ArrayList<>();

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public int getValidSince() {
        return validSince;
    }

    public void setValidSince(int validSince) {
        this.validSince = validSince;
    }

    public int getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(int lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public int getLastRefreshAt() {
        return lastRefreshAt;
    }

    public void setLastRefreshAt(int lastRefreshAt) {
        this.lastRefreshAt = lastRefreshAt;
    }

    public int getPasswordUpdatedAt() {
        return passwordUpdatedAt;
    }

    public void setPasswordUpdatedAt(int passwordUpdatedAt) {
        this.passwordUpdatedAt = passwordUpdatedAt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(String customAttributes) {
        this.customAttributes = customAttributes;
    }

    public List<ProviderUserInfo> getProviderUserInfo() {
        return providerUserInfo;
    }

    public void setProviderUserInfo(List<ProviderUserInfo> providerUserInfo) {
        this.providerUserInfo = providerUserInfo;
    }
}
