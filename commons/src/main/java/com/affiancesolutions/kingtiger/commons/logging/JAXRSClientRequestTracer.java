package com.affiancesolutions.kingtiger.commons.logging;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.client.ClientResponseContext;
import jakarta.ws.rs.client.ClientResponseFilter;
import jakarta.ws.rs.core.MediaType;
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


public class JAXRSClientRequestTracer extends JAXRSRequestTracer implements ClientRequestFilter, ClientResponseFilter, WriterInterceptor {

    private static final String CLASS_NAME = JAXRSClientRequestTracer.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);


    /**
     * Filter method called before a request has been dispatched to a client transport layer.
     * <p>
     * Filters in the filter chain are ordered according to their {@code jakarta.annotation.Priority} class-level annotation
     * value.
     *
     * @param requestContext request context.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        final String MEHOD_NAME = "filter (Client Request)";
        LOGGER.entering(CLASS_NAME, MEHOD_NAME, new Object[]{requestContext});
        final StringBuilder sb = new StringBuilder();

        if (isLoggable(Level.FINE)) {
            // Start the REQUEST trace...
            traceRequestStart(sb);

            // Trace the REQUEST host, port and URI...
            traceRequestUriInfo(sb, requestContext);

            traceRequestHeaders(sb, requestContext);

            // If the REQUEST has an entity (body), we need to defer logging unitl it is serialized ...
            if (requestContext.hasEntity()) {
                requestContext.setProperty(JAXRS_REQUEST_TRACER, sb);
            } else {
                // No entity!
                traceRequestEntity(sb, null);
                traceRequestEnd(sb);
            }
        }

        LOGGER.exiting(CLASS_NAME, MEHOD_NAME);
    }

    /**
     * Filter method called after a response has been provided for a request (either by a {@link ClientRequestFilter request
     * filter} or when the HTTP invocation returns).
     * <p>
     * Filters in the filter chain are ordered according to their {@code jakarta.annotation.Priority} class-level annotation
     * value.
     *
     * @param requestContext  request context.
     * @param responseContext response context.
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        final String METHOD_NAME = "filter (Client Response)";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{requestContext, responseContext});
        StringBuilder sb = new StringBuilder();

        if (isLoggable(Level.FINE)) {
            // Start the RESPONSE trace...
            traceResponseStart(sb);

            // Trace the REQUEST host, port and URI... and status
            traceResponseUriInfo(sb, requestContext);
            traceResponseStatusInfo(sb, responseContext);

            traceResponseHeaders(sb, responseContext);

            // Trace the RESPONSE entity (body) if available...
            traceResponseEntity(sb, getResponseBody(responseContext));

            // End the RESPONSE trace and log it...
            traceResponseEnd(sb);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    /**
     * Interceptor method wrapping calls to {@code MessageBodyWriter#writeTo} method. The parameters of the wrapped method
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
        final String METHOD_NAME = "aroundWriteTo (Client Request)";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{context});

        OutputStream originalStream = context.getOutputStream();
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        context.setOutputStream(byteStream);

        context.proceed();

        originalStream.write(byteStream.toByteArray());

        if (isLoggable(Level.FINE)) {
            final StringBuilder sb = (StringBuilder) context.getProperty(JAXRS_REQUEST_TRACER);

            traceRequestEntity(sb, byteStream.toString(StandardCharsets.ISO_8859_1));

            traceRequestEnd(sb);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    private String getResponseBody(ClientResponseContext responseContext) {
        StringBuilder sb = new StringBuilder();

        if (responseContext.hasEntity()) {

            if (responseContext.getMediaType() != null && responseContext.getMediaType().isCompatible(MediaType.MULTIPART_FORM_DATA_TYPE)) {
                sb.append("<*... NON-READABLE DATA NOT SHOWN ...*>");
            } else {
                try {
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                    final byte[] responseEntity = new byte[MAXIMUM_ENTITY_SIZE];
                    int entitySize = 0;

                    while ((entitySize = responseContext.getEntityStream().read(responseEntity)) > 0) {
                        byteStream.write(responseEntity, 0, entitySize);
                    }
                    byteStream.flush();

                    responseContext.setEntityStream(new ByteArrayInputStream(byteStream.toByteArray()));

                    sb.append(byteStream.toString(StandardCharsets.ISO_8859_1));
                } catch (IOException e) {
                    sb.append(String.format("<*... Unable to process RESPONSE body: %s ...*>", e.getLocalizedMessage()));
                }
            }
        }

        return sb.toString();
    }

    private void traceRequestStart(StringBuilder sb) {
        traceStart(sb, TYPE_REQUEST, OUTBOUND);
    }

    private void traceRequestUriInfo(StringBuilder sb, ClientRequestContext requestContext) {
        traceRequestUri(sb, TYPE_REQUEST, OUTBOUND, requestContext.getMethod(), requestContext.getUri());
    }

    private void traceRequestHeaders(StringBuilder sb, ClientRequestContext requestContext) {
        traceHTTPHeaders(sb, TYPE_REQUEST, OUTBOUND, requestContext.getStringHeaders());
    }

    private void traceRequestEntity(StringBuilder sb, String stringEntity) {
        traceEntity(sb, TYPE_REQUEST, OUTBOUND, stringEntity);
    }

    private void traceRequestEnd(StringBuilder sb) {
        traceEnd(sb, TYPE_REQUEST, OUTBOUND);
    }

    private void traceResponseStart(StringBuilder sb) {
        traceStart(sb, TYPE_RESPONSE, OUTBOUND);
    }

    private void traceResponseUriInfo(StringBuilder sb, ClientRequestContext requestContext) {
        traceRequestUri(sb, TYPE_RESPONSE, OUTBOUND, requestContext.getMethod(), requestContext.getUri());
    }

    private void traceResponseStatusInfo(StringBuilder sb, ClientResponseContext responseContext) {
        traceHTTPStatus(sb, TYPE_RESPONSE, OUTBOUND, responseContext.getStatusInfo());
    }

    private void traceResponseHeaders(StringBuilder sb, ClientResponseContext responseContext) {
        traceHTTPHeaders(sb, TYPE_RESPONSE, OUTBOUND, responseContext.getHeaders());
    }

    private void traceResponseEntity(StringBuilder sb, String stringEntity) {
        traceEntity(sb, TYPE_RESPONSE, OUTBOUND, stringEntity);
    }

    private void traceResponseEnd(StringBuilder sb) {
        traceEnd(sb, TYPE_RESPONSE, OUTBOUND);
    }
}
