package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.model;

public class GetAccountInfoRequest {

    private static final long serialVersionUID = 13264091988637406L;

    private String idToken;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
