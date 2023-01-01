package com.affiancesolutions.kingtiger.ktbtracker.server;

import jakarta.inject.Inject;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;


@Provider
//@Priority(Integer.MAX_VALUE)
public class KTBTrackerCORSFilter implements ContainerResponseFilter {

    private static final String CLASS_NAME = KTBTrackerCORSFilter.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private static final String HEADER_CORS_ALLOW_CREDS         = "Access-Control-Allow-Credentials";
    private static final String HEADER_CORS_ALLOW_HEADERS       = "Access-Control-Allow-Headers";
    private static final String HEADER_CORS_ALLOW_METHODS       = "Access-Control-Allow-Methods";
    private static final String HEADER_CORS_ALLOW_ORIGIN        = "Access-Control-Allow-Origin";
    private static final String HEADER_CORS_EXPOSE_HEADERS      = "Access-Control-Expose-Headers";
    private static final String HEADER_CORS_MAX_AGE             = "Access-Control-Max-Age";

    private static final String LIST_SEPARATOR = ", ";

    private static final String[] DEFAULT_ALLOW_ALL = new String[] { "*" };

    @Inject
    @ConfigProperty(name = HEADER_CORS_ALLOW_ORIGIN)
    private Optional<String[]> allowedOrigins;

    @Inject
    @ConfigProperty(name = HEADER_CORS_ALLOW_METHODS)
    private Optional<String[]> allowedMethods;

    @Inject
    @ConfigProperty(name = HEADER_CORS_ALLOW_HEADERS)
    private Optional<String[]> allowedHeaders;

    @Inject
    @ConfigProperty(name = HEADER_CORS_EXPOSE_HEADERS)
    private Optional<String[]> exposedHeaders;

    @Inject
    @ConfigProperty(name = HEADER_CORS_MAX_AGE)
    private Optional<Integer> maxAge;

    @Inject
    @ConfigProperty(name = HEADER_CORS_ALLOW_CREDS)
    private Optional<Boolean> allowCredentials;


    /**
     * Filter method called after a response has been provided for a request (either by a {@link ContainerRequestFilter
     * request filter} or by a matched resource method.
     * <p>
     * Filters in the filter chain are ordered according to their {@code jakarta.annotation.Priority} class-level annotation
     * value.
     * </p>
     *
     * @param requestContext  request context.
     * @param responseContext response context.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        final String METHOD_NAME = "filter (Container Response - CORS Access Control)";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{requestContext, responseContext});

        // Add CORS header "Access-Control-Allow-Origin" (REQUIRED)
        responseContext.getHeaders()
                .add(HEADER_CORS_ALLOW_ORIGIN, String.join(LIST_SEPARATOR, allowedOrigins.orElse(DEFAULT_ALLOW_ALL)));

        // Add CORS header "Access-Control-Allow-Methods" (REQUIRED)
        responseContext.getHeaders()
                .add(HEADER_CORS_ALLOW_METHODS, String.join(LIST_SEPARATOR,allowedMethods.orElse(DEFAULT_ALLOW_ALL)));

        // Add CORS header "Access-Control-Allow-Credentials" (OPTIONAL)
        //  NOTE: If this header is present the only allowed value is "true"
        allowCredentials.ifPresent(allowed -> responseContext.getHeaders()
                .add(HEADER_CORS_ALLOW_CREDS, String.valueOf(true)));

        // Add CORS header "Access-Control-Allow-Headers" (OPTIONAL)
        //  NOTE: These headers are in _addition_ to the CORS-safelisted request headers:
        //      Accept, Accept-Language, Content-Language, Content-Type
        allowedHeaders.ifPresent(strings -> responseContext.getHeaders()
                .add(HEADER_CORS_ALLOW_HEADERS, String.join(LIST_SEPARATOR, strings)));

        // Add CORS header "Access-Control-Expose-Headers" (OPTIONAL)
        //  NOTE: These are in _addition_ to the CORS-safelisted response headers:
        //      Cache-Control, Content-Language, Content-Length, Content-Type, Expires, Last-Modified, Pragma
        exposedHeaders.ifPresent(strings -> responseContext.getHeaders()
                .add(HEADER_CORS_EXPOSE_HEADERS, String.join(LIST_SEPARATOR, strings)));

        // Add CORS header "Access-Control-Max-Age" (OPTIONAL)
        //  NOTE: If omitted, defaults to 5 (seconds)
        maxAge.ifPresent(integer -> responseContext.getHeaders().add(HEADER_CORS_MAX_AGE, String.valueOf(integer)));

        // Always auto-reply 200 OK for an OPTIONS call
        if (requestContext.getMethod().equals(HttpMethod.OPTIONS)) {
            responseContext.setStatus(HttpServletResponse.SC_OK);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }
}
