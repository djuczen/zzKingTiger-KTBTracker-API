package com.affiancesolutions.kingtiger.commons.logging;

import jakarta.annotation.Priority;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.affiancesolutions.kingtiger.commons.Constants.JAXRS_REQUEST_TRACER;

@Provider
@PreMatching
@Priority(Integer.MAX_VALUE)
public class JAXRSServerRequestTracer extends JAXRSRequestTracer implements ContainerRequestFilter {

    private static final String CLASS_NAME = JAXRSServerRequestTracer.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);


    private static final String REQUEST_CONTEXT = ContainerRequestContext.class.getName();

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
        final String METHOD_NAME = "filer (Container Request)";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{requestContext});

        if (isLoggable(Level.FINE)) {
            final StringBuilder sb = new StringBuilder();

            // Start the REQUEST trace...
            traceRequestStart(sb);

            traceRequestUriInfo(sb, requestContext);

            traceHTTPHeaders(sb, TYPE_REQUEST, INBOUND, requestContext.getHeaders());

            traceRequestEntity(sb, getRequestBody(requestContext));

            traceRequestEnd(sb);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }




    private String getRequestBody(ContainerRequestContext requestContext) {
        StringBuilder sb = new StringBuilder();

        if (requestContext.hasEntity()) {
            if (requestContext.getMediaType() != null && requestContext.getMediaType().isCompatible(MediaType.MULTIPART_FORM_DATA_TYPE)) {
                sb.append("<*... NON-READABLE DATA NOT SHOWN ...*>");
            } else {
                try {
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                    final byte[] requestEntity = new byte[MAXIMUM_ENTITY_SIZE];
                    int entitySize = 0;

                    while ((entitySize = requestContext.getEntityStream().read(requestEntity)) > 0) {
                        byteStream.write(requestEntity, 0, entitySize);
                    }
                    byteStream.flush();

                    requestContext.setEntityStream(new ByteArrayInputStream(byteStream.toByteArray()));

                    sb.append(byteStream.toString(StandardCharsets.ISO_8859_1));
                } catch (IOException e) { //NOSONAR
                    sb.append(String.format("<*... Unable to process request body: [%s] ...*>", e.getLocalizedMessage()));
                }
            }
        }

        return sb.toString();
    }

    private void traceRequestStart(StringBuilder sb) {
        traceStart(sb, TYPE_REQUEST, INBOUND);
    }

    private void traceRequestUriInfo(StringBuilder sb, ContainerRequestContext requestContext) {
        traceRequestUri(sb, TYPE_REQUEST, INBOUND, requestContext.getMethod(), requestContext.getUriInfo().getRequestUri());
    }

    private void traceRequestEntity(StringBuilder sb, String stringEntity) {
        traceEntity(sb, TYPE_REQUEST, INBOUND, stringEntity);
    }

    private void traceRequestEnd(StringBuilder sb) {
        traceEnd(sb, TYPE_REQUEST, INBOUND);
    }

}
