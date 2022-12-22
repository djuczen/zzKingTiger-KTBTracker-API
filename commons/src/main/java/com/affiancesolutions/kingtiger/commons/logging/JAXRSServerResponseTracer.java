package com.affiancesolutions.kingtiger.commons.logging;

import jakarta.annotation.Priority;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.affiancesolutions.kingtiger.commons.Constants.JAXRS_REQUEST_TRACER;

@Provider
@Priority(Integer.MAX_VALUE)
public class JAXRSServerResponseTracer extends JAXRSRequestTracer implements ContainerResponseFilter, WriterInterceptor {

    private static final String CLASS_NAME = JAXRSServerResponseTracer.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

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
        final String METHOD_NAME = "filter (Container Response)";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{requestContext, responseContext});

        if (isLoggable(Level.FINE)) {
            final StringBuilder sb = new StringBuilder();

            traceResponseStart(sb);

            traceResponseUriInfo(sb, requestContext);

            traceResponseStatusInfo(sb, responseContext);

            traceHTTPHeaders(sb, TYPE_RESPONSE, INBOUND, responseContext.getStringHeaders());

            if (responseContext.hasEntity()) {
                requestContext.setProperty(JAXRS_REQUEST_TRACER, sb);
            } else {
                traceResponseEntity(sb, null);

                traceResponseEnd(sb);
            }
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    /**
     * Interceptor method wrapping calls to {@link MessageBodyWriter#writeTo} method. The parameters of the wrapped method
     * called are available from {@code context}. Implementations of this method SHOULD explicitly call
     * {@link WriterInterceptorContext#proceed} to invoke the next interceptor in the chain, and ultimately the wrapped
     * {@code MessageBodyWriter.writeTo} method.
     *
     * @param context invocation context.
     * @throws IOException             if an IO error arises or is thrown by the wrapped {@code MessageBodyWriter.writeTo}
     *                                 method.
     * @throws WebApplicationException thrown by the wrapped {@code MessageBodyWriter.writeTo} method.
     */
    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        final String METHOD_NAME = "aroundWriteTo (Container Response)";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{context});

        final OutputStream originalStream = context.getOutputStream();
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        context.setOutputStream(byteStream);

        context.proceed();

        originalStream.write(byteStream.toByteArray());

        if (isLoggable(Level.FINE)) {
            final StringBuilder sb = (StringBuilder) context.getProperty(JAXRS_REQUEST_TRACER);

            traceResponseEntity(sb, byteStream.toString(StandardCharsets.ISO_8859_1));

            traceResponseEnd(sb);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    private void traceResponseStart(StringBuilder sb) {
        traceStart(sb, TYPE_RESPONSE, INBOUND);
    }

    private void traceResponseUriInfo(StringBuilder sb, ContainerRequestContext requestContext) {
        traceRequestUri(sb, TYPE_RESPONSE, INBOUND, requestContext.getMethod(), requestContext.getUriInfo().getRequestUri());
    }

    private void traceResponseStatusInfo(StringBuilder sb, ContainerResponseContext responseContext) {
        traceHTTPStatus(sb, TYPE_RESPONSE, INBOUND, responseContext.getStatusInfo());
    }

    private void traceResponseEntity(StringBuilder sb, String stringEntity) {
        traceEntity(sb, TYPE_RESPONSE, INBOUND, stringEntity);
    }

    private void traceResponseEnd(StringBuilder sb) {
        traceEnd(sb, TYPE_RESPONSE, INBOUND);
    }
}
