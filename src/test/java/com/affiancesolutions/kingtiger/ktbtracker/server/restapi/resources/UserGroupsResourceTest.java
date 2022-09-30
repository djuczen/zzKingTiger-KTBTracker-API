package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.client.model.UserGroup;
import com.affiancesolutions.kingtiger.ktbtracker.client.services.UserGroupsService;
import com.affiancesolutions.kingtiger.ktbtracker.server.test.TestClient;
import com.affiancesolutions.kingtiger.ktbtracker.server.test.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.List;

@ExtendWith(TestConfiguration.class)
public class UserGroupsResourceTest {

    @TestClient
    public UserGroupsService userGroupsService;

    @Test
    void listUserGroups() throws IOException {

        List<UserGroup> userGroupList = userGroupsService.listUserGroups();
    }
}
