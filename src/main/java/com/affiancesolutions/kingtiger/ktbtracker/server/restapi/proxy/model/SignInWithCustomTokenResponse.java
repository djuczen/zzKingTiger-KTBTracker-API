package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.model;

public class SignInWithCustomTokenResponse {

    private static final long serialVersionUID = -1745464657564775816L;

    private String idToken;

    private String refreshToken;

    private int expiresIn;

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
