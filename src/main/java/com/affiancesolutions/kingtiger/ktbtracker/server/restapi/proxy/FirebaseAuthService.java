package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy;

import com.affiancesolutions.kingtiger.commons.logging.JAXRSClientRequestTracer;
import com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.model.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import static com.affiancesolutions.kingtiger.ktbtracker.server.restapi.proxy.Constants.*;


@RegisterRestClient(configKey = "firebase.auth")
@RegisterProvider(JAXRSClientRequestTracer.class)
@Path("/v1")
public interface FirebaseAuthService {

    @POST
    @Path("accounts:signInWithPassword")
    @Produces(MediaType.APPLICATION_JSON)
    public Response signInWithPassword(
            @QueryParam(PARAM_API_KEY) String apiKey,
            VerifyPasswordRequest signInWithPasswordlRequest
    );

    @POST
    @Path("accounts:lookup")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountInfo(
            @QueryParam(PARAM_API_KEY) String apiKey,
            GetAccountInfoRequest getAccountInfoRequest
    );

    @POST
    @Path("accounts:update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setAccountInfo(
            @QueryParam(PARAM_API_KEY) String apiKey,
            SetAccountInfoRequest setAccountInfoRequest
    );

    @POST
    @Path("accounts:signInWithCustomToken")
    @Produces(MediaType.APPLICATION_JSON)
    public Response signInWithCustomToken(
            @QueryParam(PARAM_API_KEY) String apiKey,
            SignInWithCustomTokenRequest signInWithCustomTokenRequest
    );

    @POST
    @Path("accounts:signUp")
    @Produces(MediaType.APPLICATION_JSON)
    public Response signUpNewUser(
            @QueryParam(PARAM_API_KEY) String apiKey,
            VerifyPasswordRequest signInWithPasswordRequest
    );
}
