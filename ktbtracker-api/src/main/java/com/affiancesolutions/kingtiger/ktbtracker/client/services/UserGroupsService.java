package com.affiancesolutions.kingtiger.ktbtracker.client.services;

import com.affiancesolutions.kingtiger.ktbtracker.client.model.UserGroup;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.io.IOException;
import java.util.List;

import static com.affiancesolutions.kingtiger.ktbtracker.client.Constants.*;


@RegisterRestClient(configKey = KINGTIGER_KTBTRACKER_SERVICE)
@Path("/usergroups")
public interface UserGroupsService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserGroup> listUserGroups() throws IOException;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserGroup createUserGroup(
            UserGroup userGroup
    );

    @GET
    @Path("/{groupName}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserGroup getUserGroup(
            @PathParam(PARAM_GROUP_NAME) String groupName
    ) throws IOException;

    @PUT
    @Path("/{groupName}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserGroup updateUserGroup(
            @PathParam(PARAM_GROUP_NAME) String groupName,
            UserGroup userGroup
    );

    @DELETE
    @Path("/{groupName}")
    public void deleteUserGroup(
            @PathParam(PARAM_GROUP_NAME) String groupName
    ) throws IOException;

}
