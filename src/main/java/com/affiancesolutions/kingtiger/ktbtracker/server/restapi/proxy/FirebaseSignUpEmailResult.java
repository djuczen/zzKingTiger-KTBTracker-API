package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy;

public class FirebaseSignUpEmailResult extends FirebaseSignInResult {

    private String email;

    private String localId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }
}
