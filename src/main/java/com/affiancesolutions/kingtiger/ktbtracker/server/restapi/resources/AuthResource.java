package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.FirebaseAuthService;
import com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.model.GetAccountInfoRequest;
import com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.model.SetAccountInfoRequest;
import com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.model.VerifyPasswordRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.*;
import static com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.Constants.PARAM_EMAIL;
import static com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.Constants.PARAM_PASSWORD;

@SimplyTimed
@RequestScoped
@Path("/auth")
public class AuthResource {

    private static final String CLASS_NAME = AuthResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Inject
    @RestClient
    private FirebaseAuthService firebaseAuthService;

    @Inject
    @ConfigProperty(name = "firebase.auth.apiKey")
    private String firebaseApiKey;

    @Inject
    private JsonWebToken jsonWebToken;

    @POST
    @Path("signInWithPassword")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response signInWithPassword(
            MultivaluedMap<String, String> formData
    ) {
        final String METHOD_NAME = "signInWithPassword";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, formData);

        VerifyPasswordRequest signInEmailRequest = new VerifyPasswordRequest();
        signInEmailRequest.setEmail(formData.getFirst(PARAM_EMAIL));
        signInEmailRequest.setPassword(formData.getFirst(PARAM_PASSWORD));

        Response response = firebaseAuthService.signInWithPassword(firebaseApiKey, signInEmailRequest);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, response);
        return Response.fromResponse(response).build();
    }

    @POST
    @Path("signInAnonymous")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response signInAnonymous() {
        final String METHOD_NAME = "signInAnonymous";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        VerifyPasswordRequest signInEmailRequest = new VerifyPasswordRequest();

        Response response = firebaseAuthService.signUpNewUser(firebaseApiKey, signInEmailRequest);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, response);
        return Response.fromResponse(response).build();
    }

    @POST
    @Path("signUp")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response signUpNewUser(
            MultivaluedMap<String, String> formData
    ) {
        final String METHOD_NAME = "signUpNewUser";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, formData);

        VerifyPasswordRequest signInEmailRequest = new VerifyPasswordRequest();
        signInEmailRequest.setEmail(formData.getFirst(PARAM_EMAIL));
        signInEmailRequest.setPassword(formData.getFirst(PARAM_PASSWORD));

        Response response = firebaseAuthService.signUpNewUser(firebaseApiKey, signInEmailRequest);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, response);
        return Response.fromResponse(response).build();
    }

    @PUT
    @Path("setCustomClaims")
    @RolesAllowed(ALL_AUTHENTICATED)
    public Response updateToken() throws FirebaseAuthException {
        final String METHOD_NAME = "updateToken";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);
        Map<String, Object> claims = new HashMap<>();

        claims.put("upn", jsonWebToken.getClaim(PARAM_EMAIL));
        claims.put("groups", new String[] { "admin"});

        FirebaseAuth.getInstance().setCustomUserClaims(jsonWebToken.getSubject(), claims);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
        return Response.ok().build();
    }

    @GET
    @Path("getAccountInfo")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ROLE_ADMIN, ROLE_CANDIDATE})
    public Response getAccountInfo() throws FirebaseAuthException {
        final String METHOD_NAME = "getAccountInfo";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        GetAccountInfoRequest getAccountInfoRequest = new GetAccountInfoRequest();
        getAccountInfoRequest.setIdToken(jsonWebToken.getRawToken());

        Response response = firebaseAuthService.getAccountInfo(firebaseApiKey, getAccountInfoRequest);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, response);
        return Response.fromResponse(response).build();
    }

    @POST
    @Path("setAccountInfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(ALL_AUTHENTICATED)
    public Response setAccountInfo(
            SetAccountInfoRequest request
    ) throws FirebaseAuthException {
        final String METHOD_NAME = "setAccountInfo";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, request);

        SetAccountInfoRequest setAccountInfoRequest = new SetAccountInfoRequest();
        setAccountInfoRequest.setIdToken(jsonWebToken.getRawToken());
        setAccountInfoRequest.setDisplayName(request.getDisplayName());
        setAccountInfoRequest.setPhotoUrl(request.getPhotoUrl());
        setAccountInfoRequest.setDeleteAttribute(request.getDeleteAttribute());

        Response response = firebaseAuthService.setAccountInfo(firebaseApiKey, setAccountInfoRequest);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, response);
        return Response.fromResponse(response).build();
    }
}
