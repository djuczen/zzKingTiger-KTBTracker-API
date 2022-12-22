package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.model;

import java.util.ArrayList;
import java.util.List;

public class SetAccountInfoResponse {

    private static final long serialVersionUID = 4920568732247176339L;

    private String kind;

    private String localId;

    private String email;

    private String displayName;

    private String photoUrl;

    private String passwordHash;

    private List<ProviderUserInfo> providerUserInfo = new ArrayList<>();

    private String idToken;

    private String refreshToken;

    private int expiresIn;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<ProviderUserInfo> getProviderUserInfo() {
        return providerUserInfo;
    }

    public void setProviderUserInfo(List<ProviderUserInfo> providerUserInfo) {
        this.providerUserInfo = providerUserInfo;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
