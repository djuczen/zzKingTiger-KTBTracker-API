package com.affiancesolutions.kingtiger.ktbtracker.server.test.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.client.services.CyclesService;
import com.affiancesolutions.kingtiger.ktbtracker.server.test.junit.TestClient;
import com.affiancesolutions.kingtiger.ktbtracker.server.test.junit.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TestConfiguration.class)
public class CyclesResourceTest {

    @TestClient
    public CyclesService cyclesService;

    @Test
    void getCurrentCycle() {


    }
}
