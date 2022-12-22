package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy;

public class FirebaseSignInEmailResult extends FirebaseSignUpEmailResult {

    private boolean registered;

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
