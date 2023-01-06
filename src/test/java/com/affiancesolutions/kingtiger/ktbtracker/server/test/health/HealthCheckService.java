package com.affiancesolutions.kingtiger.ktbtracker.server.test.health;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import static com.affiancesolutions.kingtiger.ktbtracker.client.Constants.KINGTIGER_KTBTRACKER_SERVICE;

@RegisterRestClient(configKey = KINGTIGER_KTBTRACKER_SERVICE)
@Path("/health")
public interface HealthCheckService extends AutoCloseable {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response healthCheck();

    @GET
    @Path("live")
    @Produces(MediaType.APPLICATION_JSON)
    public Response livenessCheck();

    @GET
    @Path("ready")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readinessCheck();
}
