package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.model;

public class SignUpNewUserRequest {

    private static final long serialVersionUID = -1L;

    private String email;

    private String password;

    private boolean returnSecureToken = true;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isReturnSecureToken() {
        return returnSecureToken;
    }

    public void setReturnSecureToken(boolean returnSecureToken) {
        this.returnSecureToken = returnSecureToken;
    }
}
