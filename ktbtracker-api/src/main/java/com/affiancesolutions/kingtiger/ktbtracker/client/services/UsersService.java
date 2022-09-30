package com.affiancesolutions.kingtiger.ktbtracker.client.services;

import com.affiancesolutions.kingtiger.ktbtracker.client.model.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.affiancesolutions.kingtiger.ktbtracker.client.Constants.*;

@RegisterRestClient(configKey = KINGTIGER_KTBTRACKER_SERVICE)
@Path("/users")
public interface UsersService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> listUsers() throws IOException ;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(
            User user
    ) throws IOException;

    @GET
    @Path("{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(
            @PathParam(PARAM_USERID) String userid
    ) throws IOException;

    @PUT
    @Path("{userid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(
            @PathParam(PARAM_USERID) String userid,
            User user
    ) throws IOException;

    @DELETE
    @Path("{userid}")
    public void deleteUser(
            @PathParam(PARAM_USERID) String userid
    ) throws IOException;

    @GET
    @Path("{userid}/groups")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getGroupsForUser(
            @PathParam(PARAM_USERID) String userid
    ) throws IOException;

    @PUT
    @Path("{userid}/groups/{groupName}")
    public void addGroupForUser(
            @PathParam(PARAM_USERID) String userid,
            @PathParam(PARAM_GROUP_NAME) String groupName
    );

    @DELETE
    @Path("{userid}/groups/{groupName}")
    public void removeGroupForUser(
            @PathParam(PARAM_USERID) String userid,
            @PathParam(PARAM_GROUP_NAME) String groupName
    );
}
