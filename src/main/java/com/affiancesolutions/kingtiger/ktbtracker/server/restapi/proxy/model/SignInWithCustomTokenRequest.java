package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.model;

public class SignInWithCustomTokenRequest {

    private static final long serialVersionUID = -7491328003936485654L;

    private String token;

    private boolean returnSecureToken = true;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isReturnSecureToken() {
        return returnSecureToken;
    }

    public void setReturnSecureToken(boolean returnSecureToken) {
        this.returnSecureToken = returnSecureToken;
    }
}
