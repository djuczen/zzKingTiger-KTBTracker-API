package com.affiancesolutions.kingtiger.ktbtracker.client.services;

import com.affiancesolutions.kingtiger.ktbtracker.client.model.Cycle;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.io.IOException;

import static com.affiancesolutions.kingtiger.ktbtracker.client.Constants.*;


@RegisterRestClient(configKey = KINGTIGER_KTBTRACKER_SERVICE)
@Path("/cycles")
public interface CyclesService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCycle(Cycle cycle) throws IOException;

    @GET
    @Path("current")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentCycle() throws IOException;

    @GET
    @Path("{cycle_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCycle(
            @PathParam(PARAM_CYCLE_ID) @DefaultValue(ZERO) Long cycleId
    ) throws IOException;

    @PUT
    @Path("{cycle_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCycle(
            @PathParam(PARAM_CYCLE_ID) @DefaultValue(ZERO) Long cycleId,
            Cycle cycle
    ) throws IOException;

    @DELETE
    @Path("{cycle_id}")
    public Response deleteCycle(
            @PathParam(PARAM_CYCLE_ID) @DefaultValue(ZERO) Long cycleId
    ) throws IOException;

}
