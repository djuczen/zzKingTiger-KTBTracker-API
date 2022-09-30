package com.affiancesolutions.kingtiger.ktbtracker.server.auth;

import com.affiancesolutions.kingtiger.ktbtracker.server.auth.impl.MicroProfileJsonWebTokenImpl;
import com.affiancesolutions.kingtiger.ktbtracker.server.auth.impl.MicroProfileSecurityContextImpl;
import com.google.firebase.auth.FirebaseAuth;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.io.IOException;
import java.util.logging.Logger;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class KTBTrackerAuthorization implements ContainerRequestFilter {

    private static final String CLASS_NAME = KTBTrackerAuthorization.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    private ResourceInfo resourceInfo;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    /**
     * Filter method called before a request has been dispatched to a resource.
     *
     * <p>
     * Filters in the filter chain are ordered according to their {@code jakarta.annotation.Priority} class-level annotation
     * value. If a request filter produces a response by calling {@link ContainerRequestContext#abortWith} method, the
     * execution of the (either pre-match or post-match) request filter chain is stopped and the response is passed to the
     * corresponding response filter chain (either pre-match or post-match). For example, a pre-match caching filter may
     * produce a response in this way, which would effectively skip any post-match request filters as well as post-match
     * response filters. Note however that a responses produced in this manner would still be processed by the pre-match
     * response filter chain.
     * </p>
     *
     * @param requestContext request context.
     * @throws IOException if an I/O exception occurs.
     * @see PreMatching
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final String METHOD_NAME = "filter (Container Request Authentication)";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{requestContext});
        String authorization = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        JsonWebToken jsonWebToken = null;

        if (authorization != null && authorization.contains("Bearer ")) {
            String bearerToken = authorization.split("\\s+")[1];

            try {
                jsonWebToken = new MicroProfileJsonWebTokenImpl(bearerToken);
                LOGGER.finest(String.format("FKeycloak: %s", jsonWebToken.getName()));
            } catch (Exception e) {
                e.printStackTrace();
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }

        requestContext.setSecurityContext(new MicroProfileSecurityContextImpl(requestContext.getSecurityContext(), jsonWebToken));
    }
}
