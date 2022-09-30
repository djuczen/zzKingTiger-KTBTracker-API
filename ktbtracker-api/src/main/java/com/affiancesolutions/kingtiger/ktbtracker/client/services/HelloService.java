package com.affiancesolutions.kingtiger.ktbtracker.client.services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import static com.affiancesolutions.kingtiger.ktbtracker.client.Constants.*;


@RegisterRestClient(configKey = KINGTIGER_KTBTRACKER_SERVICE)
@Path("/hello")
public interface HelloService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@QueryParam("name") String name);

}
