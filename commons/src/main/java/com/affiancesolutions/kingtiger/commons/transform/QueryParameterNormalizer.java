package com.affiancesolutions.kingtiger.commons.transform;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Provider
@PreMatching
public class QueryParameterNormalizer implements ContainerRequestFilter {

    private static final String CLASS_NAME = QueryParameterNormalizer.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Context
    ResourceInfo resourceInfo;

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
        final String METHOD_NAME = "filter (Container Request)";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, requestContext.getUriInfo().getRequestUri().toString());

        if (!requestContext.getUriInfo().getQueryParameters().isEmpty()) {
            UriBuilder requestUriBuilder = requestContext.getUriInfo().getRequestUriBuilder();
            requestUriBuilder.replaceQuery("");

            requestContext.getUriInfo().getQueryParameters().entrySet().stream().forEach(entry -> {
                String paramName = entry.getKey().toLowerCase(Locale.US);
                requestUriBuilder.queryParam(paramName, entry.getValue().toArray());
            });

            requestContext.setRequestUri(requestUriBuilder.build());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, requestContext.getUriInfo().getRequestUri().toString());
    }

    /**
     * Determine if a method is a JAX-RS endpoint (annotated with an HTTP method, i.e. @GET, @POST, etc.).
     *
     * @param method
     * @return
     */
    private static boolean isWebEndpoint(Method method) {
        return Stream.of(method.getAnnotations())
                .anyMatch(annotation -> annotation.annotationType().isAnnotationPresent(HttpMethod.class));
    }

    private static Stream<Method> extractMethods(Class clazz) {
        try {
            Method[] methods = clazz.getMethods();
            if (methods.length > 0) {
                return Stream.of(methods);
            }
        } catch (Exception | Error e) {

        }
        return Stream.empty();
    }

    private Stream<QueryParam> extractQueryParams(Method method) {
        return Stream.of(method.getParameterAnnotations()).flatMap(Stream::of)
                .filter(annotation -> annotation.annotationType().isAssignableFrom(QueryParam.class))
                .map(QueryParam.class::cast);
    }
}
