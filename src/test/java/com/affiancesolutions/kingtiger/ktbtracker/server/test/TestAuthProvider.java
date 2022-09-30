package com.affiancesolutions.kingtiger.ktbtracker.server.test;

import com.affiancesolutions.kingtiger.commons.logging.JAXRSClientRequestTracer;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.logging.Logger;

public class TestAuthProvider implements ClientRequestFilter {

    private static final String CLASS_NAME = TestAuthProvider.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private static final String OAUTH2_TOKENENDPOINT =
            ConfigProvider.getConfig().getValue("SEC_SSO_OAUTH2_TOKENENDPOINT", String.class);

    private static final String CLIENT_ID =
            ConfigProvider.getConfig().getValue("SEC_SSO_OIDC_CLIENTID", String.class);

    private static final String CLIENT_SECRET =
            ConfigProvider.getConfig().getValue("SEC_SSO_OIDC_CLIENTSECRET", String.class);

    private static String accessToken;

    private static Instant expiresIn;


    public static String getAccessToken() {
        if (accessToken == null || Instant.now().isAfter(expiresIn)) {
            synchronized (TestAuthProvider.class) {
                if (accessToken == null || Instant.now().isAfter(expiresIn)) {
                    Form tokenForm = new Form();
                    tokenForm.param("client_id", CLIENT_ID);
                    tokenForm.param("client_secret", CLIENT_SECRET);
                    tokenForm.param("grant_type", "password");
                    tokenForm.param("username", "test-user@email.com");
                    tokenForm.param("password", "fMGQXeg3LNAs");

                    Invocation.Builder builder = TestConfiguration.getClient()
                            .target(OAUTH2_TOKENENDPOINT)
                            .request(MediaType.APPLICATION_JSON_TYPE);

                    Response response = builder
                            //.header(HttpHeaders.AUTHORIZATION, getHTTPBasicAuth())
                            .post(Entity.form(tokenForm));

                    JsonObject tokenResponse = response.readEntity(JsonObject.class);
                    accessToken = tokenResponse.getString("access_token");
                    expiresIn = Instant.now().plus(tokenResponse.getInt("expires_in"), ChronoUnit.MINUTES);
                }
            }
        }
        return accessToken != null ? accessToken : "";
    }

    public static String getHTTPBearerAuth() {
        return String.format("Bearer %s", getAccessToken());
    }

    public static String getHTTPBasicAuth() {
        return String.format("Basic %s", Base64.getEncoder()
                .encodeToString(String.format("%s:%s", CLIENT_ID, CLIENT_SECRET).getBytes(StandardCharsets.UTF_8)));
    }

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
        final String METHOD_NAME = "filter";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{requestContext});

        if (requestContext.getHeaderString(HttpHeaders.AUTHORIZATION) == null) {
            requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, getHTTPBearerAuth());
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }
}
