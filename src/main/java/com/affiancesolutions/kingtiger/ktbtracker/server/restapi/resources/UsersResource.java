package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.Constants;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.User;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.UserGroup;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.UserGroupsDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.UsersDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorStatus;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@SimplyTimed
@RequestScoped
@RolesAllowed(Constants.ALL_AUTHENTICATED)
@Path("/users")
public class UsersResource {

    private static final String CLASS_NAME = UsersResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @Context
    private ResourceContext resourceContext;

    @Inject
    private UsersDAO usersDAO;

    @Inject
    private UserGroupsDAO userGroupsDAO;


    /**
     * Return a list of registered users.
     *
     * @return returns a {@link List} of registered users ({@link User})
     *
     * @throws IOException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(Constants.ALL_AUTHENTICATED)
    public Response listUsers() throws IOException {
        final String METHOD_NAME = "listUsers";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        List<User> resultList = usersDAO.findAll();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resultList);
        return Response.ok(resultList).build();
    }

    /**
     * @param user
     * @return
     * @throws IOException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(Constants.ALL_AUTHENTICATED)
    @Transactional
    public Response createUser(
            User user
    ) throws IOException {
        final String METHOD_NAME = "createUser";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, user);

        // If the user already exists, return 409 Conflict...
        if (usersDAO.find(user.getId()) != null) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorStatus(Response.Status.CONFLICT.getStatusCode(),
                            String.format("User '%s' is already registered", user.getId()))).build());
        }

        User result = usersDAO.create(user);
        URI location = uriInfo.getAbsolutePathBuilder().path(user.getId()).build();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return Response.created(location).entity(result).build();
    }

    /**
     * @param userid
     * @return
     * @throws IOException
     */
    @GET
    @Path("{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(Constants.ALL_AUTHENTICATED)
    public Response getUser(
            @PathParam(Constants.PARAM_USERID) String userid
    ) throws IOException {
        final String METHOD_NAME = "getUser";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, userid);

        User result = usersDAO.find(userid);
        if (result == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("User '%s' is not registered", userid))).build());
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
    @Path("{userid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(Constants.ALL_AUTHENTICATED)
    @Transactional
    public Response updateUser(
            @PathParam(Constants.PARAM_USERID) String userid,
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
    @Path("{userid}")
    @RolesAllowed(Constants.ALL_AUTHENTICATED)
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
                            String.format("User '%s' is not registered", userid))).build());
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
    @Path("{userid}/groups")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getGroupsForUser(
            @PathParam(Constants.PARAM_USERID) String userid
    ) throws IOException {
        final String METHOD_NAME = "getGroupsForUser";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, userid);

        // First make sure the user exists
        User user = usersDAO.find(userid);
        if (user == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("User '%s' is not registered", userid))).build());
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
    @Path("{userid}/groups/{groupName}")
    @RolesAllowed(Constants.ALL_AUTHENTICATED)
    @Transactional
    public Response addGroupForUser(
            @PathParam(Constants.PARAM_USERID) String userid,
            @PathParam(Constants.PARAM_GROUP_NAME) String groupName
    ) {
        final String METHOD_NAME = "addGroupForUser";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{userid, groupName});

        // First make sure the user exists
        User user = usersDAO.find(userid);
        if (user == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("User '%s' is not registered", userid))).build());
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
    @Path("{userid}/groups/{groupName}")
    @RolesAllowed(Constants.ALL_AUTHENTICATED)
    @Transactional
    public Response removeGroupForUser(
            @PathParam(Constants.PARAM_USERID) String userid,
            @PathParam(Constants.PARAM_GROUP_NAME) String groupName
    ) {
        final String METHOD_NAME = "removeGroupForUser";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{userid, groupName});

        // First make sure the user exists
        User user = usersDAO.find(userid);
        if (user == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorStatus(Response.Status.NOT_FOUND.getStatusCode(),
                            String.format("User '%s' is not registered", userid))).build());
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
