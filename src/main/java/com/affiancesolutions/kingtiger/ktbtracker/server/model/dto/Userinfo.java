package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * This is a Data Transfer Object (DTO) representing a {@code FirebaseToken}.
 *
 */
public class Userinfo implements Serializable {

    private static final long serialVersionUID = -1L;

    private String uid;

    private String name;

    private String picture;

    private String email;

    private boolean isEmailVerified;

    private String issuer;

    private String tenantId;

    private Map<String, Object> claims = new HashMap<>();


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Map<String, Object> getClaims() {
        return claims;
    }

    public void setClaims(Map<String, Object> claims) {
        this.claims = claims;
    }

    @Override
    public String toString() {
        return String.format("Userinfo(uid=%s, name=%s, email=%s, isEmailVerified=%s)", uid, name, email, isEmailVerified);
    }
}
