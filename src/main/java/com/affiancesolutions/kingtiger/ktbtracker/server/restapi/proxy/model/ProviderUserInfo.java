package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.model;

public class ProviderUserInfo {

    private static final long serialVersionUID = -1936351497361476664L;

    private String email;

    private String rawId;

    private String federatedId;

    private String providerId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRawId() {
        return rawId;
    }

    public void setRawId(String rawId) {
        this.rawId = rawId;
    }

    public String getFederatedId() {
        return federatedId;
    }

    public void setFederatedId(String federatedId) {
        this.federatedId = federatedId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
