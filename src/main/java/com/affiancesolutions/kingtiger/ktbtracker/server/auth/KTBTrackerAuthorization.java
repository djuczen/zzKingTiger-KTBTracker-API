package com.affiancesolutions.kingtiger.ktbtracker.server.auth;

import com.affiancesolutions.kingtiger.ktbtracker.server.auth.impl.MicroProfileSecurityContextImpl;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.logging.Logger;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class KTBTrackerAuthorization implements ContainerRequestFilter {

    private static final String CLASS_NAME = KTBTrackerAuthorization.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

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

        requestContext.setSecurityContext(new MicroProfileSecurityContextImpl(requestContext.getSecurityContext()));

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }
}
