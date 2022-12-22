package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.model;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class SetAccountInfoRequest {

    private static final long serialVersionUID = 1427359183927552755L;

    private String idToken;

    private String displayName;

    private String photoUrl;

    private List<String> deleteAttribute = new ArrayList<>();

    private boolean returnSecureToken = true;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
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

    public List<String> getDeleteAttribute() {
        return deleteAttribute;
    }

    public void setDeleteAttribute(List<String> deleteAttribute) {
        this.deleteAttribute = deleteAttribute;
    }

    public boolean isReturnSecureToken() {
        return returnSecureToken;
    }

    public void setReturnSecureToken(boolean returnSecureToken) {
        this.returnSecureToken = returnSecureToken;
    }
}
