package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;


import java.util.logging.Logger;

import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.ALL_AUTHENTICATED;

@SimplyTimed
@RequestScoped
@RolesAllowed(ALL_AUTHENTICATED)
@Path("/userinfo")
public class UserinfoResource {

    private static final String CLASS_NAME = UserinfoResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private SecurityContext securityContext;


    @GET
    @Path("me")
    public Response getUserinfoSelf() {
        final String METHOD_NAME = "getUserinfoSelf";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, securityContext.getUserPrincipal().getName());


        LOGGER.exiting(CLASS_NAME, METHOD_NAME, null);
        return Response.ok().build();
    }
}
