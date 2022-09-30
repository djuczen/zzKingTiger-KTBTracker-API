package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.UserGroup;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.UserGroupsDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorResult;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorStatus;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.PARAM_GROUP_NAME;

@SimplyTimed
@RequestScoped
@Path("/usergroups")
public class UserGroupsResource {

    private static final String CLASS_NAME = UserGroupsResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private UriInfo uriInfo;

    @Inject
    private UserGroupsDAO userGroupsDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listUserGroups() throws IOException {
        final String METHOD_NAME = "listUserGroups";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        List<UserGroup> resultList = userGroupsDAO.findAll();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return Response.ok(resultList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createUserGroup(
            UserGroup userGroup
    ) {
        final String METHOD_NAME = "createUserGroup";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        // If the user group already exists, return a 409 Conflict...
        if (userGroupsDAO.findByName(userGroup.getName()) != null) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResult(new ErrorStatus(Response.Status.CONFLICT.getStatusCode(),
                            String.format("User group '%s' already exists", userGroup.getName()))))
                    .build());
        }

        // Ensure the ID field is 0!
        userGroup.setId(0L);

        userGroup = userGroupsDAO.create(userGroup);
        URI location = uriInfo.getAbsolutePathBuilder().path(userGroup.getName()).build();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, userGroup);
        return Response.created(location).build();
    }

    @GET
    @Path("/{groupName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserGroup(
            @PathParam(PARAM_GROUP_NAME) String groupName
    ) throws IOException {
        final String METHOD_NAME = "getUserGroup";
        LOGGER.exiting(CLASS_NAME, METHOD_NAME, new Object[]{groupName});

        UserGroup result = userGroupsDAO.findByName(groupName);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return Response.ok(result).build();
    }

    @PUT
    @Path("/{groupName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updatetUserGroup(
            @PathParam(PARAM_GROUP_NAME) String groupName,
            UserGroup userGroup
    ) {
        final String METHOD_NAME = "updateUserGroup";
        LOGGER.exiting(CLASS_NAME, METHOD_NAME, new Object[]{groupName});

        // Ensure that the group name is correct!
        userGroup.setName(groupName);

        UserGroup result = userGroupsDAO.findByName(groupName);
        if (result == null) {
            userGroup.setId(0L);
            result = userGroupsDAO.create(userGroup);
        } else {
            userGroup.setId(result.getId());
            result = userGroupsDAO.update(userGroup);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return Response.ok(result).build();
    }

    @DELETE
    @Path("/{groupName}")
    @Transactional
    public Response deleteUserGroup(
            @PathParam(PARAM_GROUP_NAME) String groupName
    ) throws IOException {
        final String METHOD_NAME = "deleteUserGroup";
        LOGGER.exiting(CLASS_NAME, METHOD_NAME, new Object[]{groupName});

        userGroupsDAO.deleteByName(groupName);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
        return Response.noContent().build();
    }
}
