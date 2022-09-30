package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/hello")
public class HelloResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello(@QueryParam("name") String name) {

        Response response = Response.ok(String.format("Hello, %s!", name != null ? name : "world")).build();
        return response;
    }
}
