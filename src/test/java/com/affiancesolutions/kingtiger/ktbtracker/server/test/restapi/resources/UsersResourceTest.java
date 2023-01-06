package com.affiancesolutions.kingtiger.ktbtracker.server.test.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.client.model.User;
import com.affiancesolutions.kingtiger.ktbtracker.client.services.UsersService;
import com.affiancesolutions.kingtiger.ktbtracker.server.test.junit.TestClient;
import com.affiancesolutions.kingtiger.ktbtracker.server.test.junit.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.List;

@ExtendWith(TestConfiguration.class)
public class UsersResourceTest {

    @TestClient
    public UsersService usersService;

    @Test
    void listUsers() throws IOException {
        List<User> users = usersService.listUsers();
    }
}
