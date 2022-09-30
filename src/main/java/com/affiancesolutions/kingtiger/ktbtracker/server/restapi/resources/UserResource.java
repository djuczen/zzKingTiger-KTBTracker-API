package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.User;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.UserGroup;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.UserGroupsDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.UsersDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorStatus;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.*;


@SimplyTimed
@RequestScoped
public class UserResource {

    private static final String CLASS_NAME = UserResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @PathParam(PARAM_USERID)
    private String userid;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @Inject
    private UsersDAO usersDAO;

    @Inject
    private UserGroupsDAO userGroupsDAO;
/*
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
*/
    /**
     * @param userid
     * @return
     * @throws IOException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(ALL_AUTHENTICATED)
    public Response getUser(
            @PathParam(PARAM_USERID) String userid
    ) throws IOException {
        final String METHOD_NAME = "getUser";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, userid);

        // If the special "me" is requested, then resolve the current caller userid
        if (userid.equals("me")) {
            userid = securityContext.getUserPrincipal().getName();
        }

        User result = usersDAO.find(userid);
        if (result == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("User '%s' does not exist", userid))).build());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return Response.ok(result).build();
    }

    /**
     * @param userid
     * @param user
     * @return
     * @throws IOException
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(ALL_AUTHENTICATED)
    @Transactional
    public Response updateUser(
            @PathParam(PARAM_USERID) String userid,
            User user
    ) throws IOException {
        final String METHOD_NAME = "updateUser";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, userid);
        User result = null;

        if (usersDAO.find(userid) != null) {
            result = usersDAO.update(user);
        } else {
            result = usersDAO.create(user);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return Response.ok(result).build();
    }

    /**
     * @param userid
     * @return
     * @throws IOException
     */
    @DELETE
    @RolesAllowed(ALL_AUTHENTICATED)
    @Transactional
    public Response deleteUser(
            @PathParam("userid") String userid
    ) throws IOException {
        final String METHOD_NAME = "deleteUser";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, userid);

        // First make sure the user exists
        User user = usersDAO.find(userid);
        if (user == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("User '%s' does not exist", userid))).build());
        }

        usersDAO.delete(user);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
        return Response.noContent().build();
    }

    /**
     * @param userid
     * @return
     * @throws IOException
     */
    @GET
    @Path("groups")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(ALL_AUTHENTICATED)
    public Response getGroupsForUser(
            @PathParam(PARAM_USERID) String userid
    ) throws IOException {
        final String METHOD_NAME = "getGroupsForUser";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, userid);

        // First make sure the user exists
        User user = usersDAO.find(userid);
        if (user == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("User '%s' does not exist", userid))).build());
        }

        List<String> resultList = user.getGroups().stream().map(UserGroup::getName).collect(Collectors.toList());

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return Response.ok(resultList).build();
    }

    /**
     * @param userid
     * @param groupName
     * @return
     */
    @PUT
    @Path("groups/{groupName}")
    @RolesAllowed(ALL_AUTHENTICATED)
    @Transactional
    public Response addGroupForUser(
            @PathParam(PARAM_USERID) String userid,
            @PathParam(PARAM_GROUP_NAME) String groupName
    ) {
        final String METHOD_NAME = "addGroupForUser";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{userid, groupName});

        // First make sure the user exists
        User user = usersDAO.find(userid);
        if (user == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("User '%s' does not exist", userid))).build());
        }

        // If the user exists, make sure the group exists
        UserGroup userGroup = userGroupsDAO.findByName(groupName);
        if (userGroup == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("User '%s' is not a member of group '%s'", userid, groupName))).build());
        }

        // The user and group exist, so add the user to the group
        user.addGroup(userGroup);
        user = usersDAO.update(user);
        userGroup = userGroupsDAO.update(userGroup);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
        return Response.noContent().build();
    }

    /**
     * @param userid
     * @param groupName
     * @return
     */
    @DELETE
    @Path("groups/{groupName}")
    @RolesAllowed(ALL_AUTHENTICATED)
    @Transactional
    public Response removeGroupForUser(
            @PathParam(PARAM_USERID) String userid,
            @PathParam(PARAM_GROUP_NAME) String groupName
    ) {
        final String METHOD_NAME = "removeGroupForUser";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{userid, groupName});

        // First make sure the user exists
        User user = usersDAO.find(userid);
        if (user == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("User '%s' does not exist", userid))).build());
        }

        if (!user.getGroups().contains(groupName)) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("User '%s' is not a member of group '%s'", userid, groupName))).build());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
        return Response.noContent().build();
    }
}
