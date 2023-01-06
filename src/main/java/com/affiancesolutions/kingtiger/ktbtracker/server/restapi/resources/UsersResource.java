package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.Constants;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.UserResult;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.User;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.entity.UserGroup;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.UserGroupsDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dao.UsersDAO;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.dto.ErrorStatus;
import com.google.firebase.auth.ListUsersPage;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import java.io.IOException;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.PARAM_ME;
import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.PARAM_USERID;

@SimplyTimed
@RequestScoped
@RolesAllowed(Constants.ALL_AUTHENTICATED)
@Path("/users")
public class UsersResource {

    private static final String CLASS_NAME = UsersResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private static final Comparator<UserResult> FullNameComparator = Comparator.comparing(UserResult::getFullName);

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

    @Inject
    private UserResource userResource;


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
        ListUsersPage page;

        List<UserResult> resultList = usersDAO.findAll().stream().map(UserResult::new)
                .sorted(FullNameComparator)
                .collect(Collectors.toList());

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
        if (usersDAO.find(user.getUserId()) != null) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorStatus(Response.Status.CONFLICT.getStatusCode(),
                            String.format("User '%s' is already registered", user.getUserId()))).build());
        }

        User result = usersDAO.create(user);
        URI location = uriInfo.getAbsolutePathBuilder().path(user.getUserId()).build();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, result);
        return Response.created(location).entity(result).build();
    }

    @Path("{userid}")
    public UserResource getUserResource(
            @PathParam(PARAM_USERID) String userid
    ) {
        final String METHOD_NAME = "getUserResource";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, userid);
        JsonWebToken jsonWebToken = (JsonWebToken) securityContext.getUserPrincipal();
        User user;

        if (userid.equals(PARAM_ME)) {
            user = usersDAO.find(jsonWebToken.getSubject());
        } else {
            user = usersDAO.find(userid);
        }

        UserResource resource = resourceContext.initResource(userResource);
        userResource.setUser(user);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, resource);
        return resource;
    }
}
