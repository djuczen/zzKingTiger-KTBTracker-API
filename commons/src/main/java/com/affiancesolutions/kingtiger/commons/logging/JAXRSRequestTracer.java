package com.affiancesolutions.kingtiger.commons.logging;

import com.affiancesolutions.kingtiger.commons.Constants;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JAXRSRequestTracer {

    private static final Logger TRACER = Logger.getLogger(Constants.JAXRS_REQUEST_TRACER);

    private static final String REQUEST_CONTEXT = ContainerRequestContext.class.getName();

    protected static final String TYPE_REQUEST = "REQUEST ";
    protected static final String TYPE_RESPONSE = "RESPONSE";

    protected static final String INBOUND = "Inbound";
    protected static final String OUTBOUND = "Outbound";

    protected static final int MAXIMUM_ENTITY_SIZE = 32 * 1024;


    protected boolean isLoggable(Level level) {
        return TRACER.isLoggable(level);
    }

    protected void traceRequestUri(StringBuilder sb, String type, String direction, String method, URI requestUri) {
        if (TRACER.isLoggable(Level.FINE)) {
            sb.append(String.format("JAX-RS %s (%s): %s %s%s%n",
                    type,
                    direction,
                    method,
                    requestUri.getPath(),
                    requestUri.getQuery() != null ? "?" + requestUri.getQuery() : "",
                    requestUri.getFragment() != null ? "#" + requestUri.getFragment() : ""));
            sb.append(String.format("JAX-RS %s (%s): Host: %s%n",
                    type,
                    direction,
                    requestUri.getAuthority()));
        }
    }

    protected void traceHTTPStatus(StringBuilder sb, String type, String direction, Response.StatusType statusType) {
        if (TRACER.isLoggable(Level.FINE)) {
            sb.append(String.format("JAX-RS %s (%s): Status %d %s%n",
                    type,
                    direction,
                    statusType.getStatusCode(),
                    statusType.getReasonPhrase()));
        }
    }

    protected void traceStart(StringBuilder sb, String type, String direction) {
        if (TRACER.isLoggable(Level.FINE)) {
            sb.append(String.format("%nJAX-RS %s (%s): ------ START ------%n",
                    type,
                    direction));
        }
    }

    protected void traceEnd(StringBuilder sb, String type, String direction) {
        if (TRACER.isLoggable(Level.FINE)) {
            sb.append(String.format("JAX-RS %s (%s): ------  END  ------",
                    type,
                    direction));

            TRACER.log(TRACER.getLevel(), sb.toString());
        }
    }

    protected void traceHTTPHeaders(StringBuilder sb, String type, String direction, MultivaluedMap<String, String> headers) {
        if (TRACER.isLoggable(Level.FINE)) {
            if (TRACER.isLoggable(Level.FINER)) {
                sb.append(String.format("JAX-RS %s (%s): HTTP Headers:%n",
                        type,
                        direction));
                for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                    sb.append(String.format("JAX-RS %s (%s):    %s: %s%n",
                            type,
                            direction,
                            entry.getKey(),
                            entry.getKey().equalsIgnoreCase(HttpHeaders.AUTHORIZATION) ?
                                    TRACER.isLoggable(Level.FINEST) ?
                                            String.join(", ", entry.getValue()) :
                                            "*REDACTED*" :
                                    String.join(", ", entry.getValue())));
                }
            } else {
                sb.append(String.format("JAX-RS %s (%s): HTTP Headers: *REDACTED*%n",
                        type,
                        direction));
            }
        }
    }

    protected void traceEntity(StringBuilder sb, String type, String direction, String entityString) {
        if (TRACER.isLoggable(Level.FINE)) {
            if (TRACER.isLoggable(Level.FINER) && (entityString != null && !entityString.isEmpty())) {
                sb.append(String.format("JAX-RS %s (%s): BODY:%n%s%n",
                        type,
                        direction,
                        TRACER.isLoggable(Level.FINEST) ? entityString : abbrev(entityString)));
            } else {
                sb.append(String.format("JAX-RS %s (%s): BODY: %s%n",
                        type,
                        direction,
                        (entityString != null && !entityString.isEmpty()) ? "*REDACTED*" : "N/A"));
            }
        }
    }

    protected void copy(InputStream source, OutputStream target) throws IOException {
        byte[] buf = new byte[MAXIMUM_ENTITY_SIZE];
        int length;

        while ((length = source.read(buf)) > 0) {
            target.write(buf, 0, length);
        }
    }

    private String abbrev(String string) {
        return abbrev(string, 120);
    }

    private String abbrev(String string, int limit) {
        if (string == null || string.length() <= limit) {
            return string;
        }

        return string.substring(0, (limit / 2)) + " ... " + string.substring(string.length() - (limit / 2));
    }
}
