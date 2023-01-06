package com.affiancesolutions.kingtiger.ktbtracker.server.test.junit;

import com.affiancesolutions.kingtiger.ktbtracker.server.KTBTrackerApplication;
import jakarta.json.JsonObject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.ConfigProvider;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class TestAuthProvider implements ClientRequestFilter {

    private static final String CLASS_NAME = TestAuthProvider.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private static final URL APPLICATION_URL =
            ConfigProvider.getConfig().getValue("kingtiger.ktbtracker.svc.service/mp-rest/url", URL.class);

    private static final String CLIENT_ID =
            ConfigProvider.getConfig().getValue("SEC_SSO_OIDC_CLIENTID", String.class);

    private static final String CLIENT_SECRET =
            ConfigProvider.getConfig().getValue("SEC_SSO_OIDC_CLIENTSECRET", String.class);

    private static final Lock LOCK = new ReentrantLock();

    private static volatile String idToken;

    private static volatile Instant expiresIn;


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
        final String METHOD_NAME = "filter (Client Request Authentication)";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{requestContext});

        if (requestContext.getHeaderString(HttpHeaders.AUTHORIZATION) == null) {
            requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, TestConfiguration.getHTTPBearerAuth());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    private String getHTTPBearerAuth() {
        return String.format("Bearer %s", getIdToken());
    }

    private String getIdToken() {
        final String METHOD_NAME = "getIdToken";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        if (idToken == null || Instant.now().isAfter(expiresIn)) {
            LOCK.lock();
            try {
                if (idToken == null || Instant.now().isAfter(expiresIn)) {
                    Form tokenForm = new Form();
                    tokenForm.param("email", CLIENT_ID);
                    tokenForm.param("password", CLIENT_SECRET);

                    Invocation.Builder builder = TestConfiguration.getClient()
                            .target(TestConfiguration.getApplicationBaseUri().resolve("api/auth/signInWithPassword"))
                            .request(MediaType.APPLICATION_JSON_TYPE);

                    Response response = builder
                            //.header(HttpHeaders.AUTHORIZATION, getHTTPBasicAuth())
                            .post(Entity.form(tokenForm));

                    JsonObject tokenResponse = response.readEntity(JsonObject.class);
                    idToken = tokenResponse.getString("idToken");
                    //expiresIn = Instant.now().plus(tokenResponse.getInt("expiresIn"), ChronoUnit.SECONDS);
                    expiresIn = Instant.now().plus(Integer.parseInt(tokenResponse.getString("expiresIn")), ChronoUnit.SECONDS);

                    //refreshToken = tokenResponse.getString("refresh_token");
                    //refreshExpiresIn = Instant.now().plus(tokenResponse.getInt("refresh_expires_in"), ChronoUnit.MINUTES);
                }
            } finally {
                LOCK.unlock();
            }
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, idToken != null ? idToken : "");
        return idToken != null ? idToken : "";
    }
}
