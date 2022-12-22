package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.model;

import java.util.ArrayList;
import java.util.List;

public class GetAccountInfoResponse {

    private static final long serialVersionUID = -6292534992734068569L;

    private String kind;

    private List<UserAccountInfo> users = new ArrayList<>();

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<UserAccountInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserAccountInfo> users) {
        this.users = users;
    }
}
