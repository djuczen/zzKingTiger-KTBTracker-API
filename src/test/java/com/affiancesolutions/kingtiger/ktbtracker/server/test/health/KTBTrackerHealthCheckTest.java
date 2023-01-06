package com.affiancesolutions.kingtiger.ktbtracker.server.test.health;

import com.affiancesolutions.kingtiger.ktbtracker.server.test.junit.TestClient;
import com.affiancesolutions.kingtiger.ktbtracker.server.test.junit.TestConfiguration;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static jakarta.ws.rs.core.Response.Status.Family.SERVER_ERROR;


@ExtendWith(TestConfiguration.class)
public class KTBTrackerHealthCheckTest {

    @TestClient
    public HealthCheckService healthCheckService;


    @Test
    void healthCheck() {
        try {
            Response response = healthCheckService.healthCheck();
        } catch (WebApplicationException e) {
            Assertions.assertNotEquals(e.getResponse().getStatusInfo().getFamily(), SERVER_ERROR);
        }
    }

    @Test
    void livenessCheck() {
        try {
            Response response = healthCheckService.livenessCheck();
        } catch (WebApplicationException e) {
            Assertions.assertNotEquals(e.getResponse().getStatusInfo().getFamily(), SERVER_ERROR);
        }
    }

    @Test
    void readinessCheck() {
        try {
            Response response = healthCheckService.readinessCheck();
        } catch (WebApplicationException e) {
            Assertions.assertNotEquals(e.getResponse().getStatusInfo().getFamily(), SERVER_ERROR);
        }
    }
}
