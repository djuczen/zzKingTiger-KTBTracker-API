package com.affiancesolutions.security.mp.jwt;

import com.affiancesolutions.security.mp.jwt.impl.MicroProfileJsonWebTokenImpl;
import com.affiancesolutions.security.mp.jwt.impl.MicroProfileSecurityContextImpl;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.annotation.Priority;
import jakarta.annotation.security.RolesAllowed;
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
import org.jose4j.jwt.consumer.ErrorCodeValidator;
import org.jose4j.jwt.consumer.InvalidJwtException;

import java.io.IOException;
import java.util.logging.Logger;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class MicroProfileJwtProvider implements ContainerRequestFilter {

    private static final String CLASS_NAME = MicroProfileJwtProvider.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private static final String BEARER_REGEX = "Bearer ([a-zA-Z0-9]|-|_)+\\.([a-zA-Z0-9]|-|_)+\\.([a-zA-Z0-9]|-|_|=)+";

    private static final String BEARER_REALM = "Bearer realm=\"MP-JWT\"";

    @Context
    private ResourceInfo resourceInfo;


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
        JsonWebToken jsonWebToken = null;

        String authorization = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorization != null && authorization.matches(BEARER_REGEX)) {
            String bearerToken = authorization.split(" ")[1];

            try {
                jsonWebToken = new MicroProfileJsonWebTokenImpl(bearerToken);
            } catch (InvalidJwtException e) {
                // If the resource method (or class) is annotated with @RolesAllowed we MUST reject the request
                if (resourceInfo.getResourceMethod().isAnnotationPresent(RolesAllowed.class) ||
                        resourceInfo.getResourceClass().isAnnotationPresent(RolesAllowed.class)) {

                    for (ErrorCodeValidator.Error error : e.getErrorDetails()) {
                        LOGGER.severe(String.format("MPJWT59%02dE: %s", error.getErrorCode(), error.getErrorMessage()));
                    }

                    Response response = Response.status(Response.Status.UNAUTHORIZED)
                            .header(HttpHeaders.WWW_AUTHENTICATE, BEARER_REALM)
                            .build();

                    LOGGER.exiting(CLASS_NAME, METHOD_NAME, "Unauthorized");
                    requestContext.abortWith(response);
                }

                throw new RuntimeException(e);
            } catch (FirebaseAuthException e) {
                LOGGER.severe(String.format("MPJWT69%02dE: %s", e.getAuthErrorCode().ordinal(), e.getLocalizedMessage()));

                Response response = Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, BEARER_REALM)
                        .build();

                LOGGER.exiting(CLASS_NAME, METHOD_NAME, "Unauthorized");
                requestContext.abortWith(response);
            }
        }

        requestContext.setSecurityContext(
                new MicroProfileSecurityContextImpl(requestContext.getSecurityContext(), jsonWebToken));

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }
}
