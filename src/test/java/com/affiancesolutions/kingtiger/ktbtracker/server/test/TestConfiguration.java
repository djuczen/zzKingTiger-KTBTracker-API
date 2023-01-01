package com.affiancesolutions.kingtiger.ktbtracker.server.test;

import com.affiancesolutions.kingtiger.ktbtracker.server.KTBTrackerApplication;
import com.affiancesolutions.kingtiger.commons.logging.JAXRSClientRequestTracer;
import jakarta.json.JsonObject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TestConfiguration implements BeforeAllCallback, BeforeEachCallback, ExtensionContext.Store.CloseableResource, ClientRequestFilter {

    private static final String CLASS_NAME = TestConfiguration.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private static final String APPLICATION_PATH = KTBTrackerApplication.class.getAnnotation(ApplicationPath.class).value();

    private static final String CONTEXT_ROOT = "/";

    private static final String OIDC_DISCOVERYENDPOINT = "";

    private static final String OAUTH2_TOKENENDPOINT = System.getenv("SEC_SSO_OAUTH2_TOKENENDPOINT");

    private static SSLContext sslContext;

    private static Client client;

    private String idToken;

    private String accessToken;

    private Instant expiresIn;

    private String refreshToken;

    private Instant refreshExpiresIn;

    static {
        //
        // Initialize our SSLContext to accept ALL client and server certificates...
        //
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            // Accept ALL Client Certificates
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            // Accept ALL Server Certificates
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            }, new SecureRandom());

        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        //
        // Initialize our JAX-RS Client to accept all certificates and all host names...
        //
        client = ClientBuilder.newBuilder()
                .sslContext(sslContext)
                .hostnameVerifier(((hostname, session) -> true))
                .register(JAXRSClientRequestTracer.class, Integer.MAX_VALUE)
                .build();
    }

    public static SSLContext getSslContext() {
        return sslContext;
    }

    public static Client getClient() {
        return client;
    }

    public String getAccessToken() {
        if (accessToken == null || Instant.now().isAfter(expiresIn)) {
            synchronized (this) {
                if (accessToken == null || Instant.now().isAfter(expiresIn)) {
                    Form tokenForm = new Form();
                    tokenForm.param("client_id", "kingtigerblackbelt.com");
                    tokenForm.param("client_secret", "72898b92-8257-4637-9102-01027a5ce9fd");
                    tokenForm.param("grant_type", "password");
                    tokenForm.param("username", "test-user@email.com");
                    tokenForm.param("password", "fMGQXeg3LNAs");

                    Invocation.Builder builder = getClient()
                            .target(OAUTH2_TOKENENDPOINT)
                            .request(MediaType.APPLICATION_JSON_TYPE);

                    Response response = builder
                            //.header(HttpHeaders.AUTHORIZATION, getHTTPBasicAuth())
                            .post(Entity.form(tokenForm));

                    JsonObject tokenResponse = response.readEntity(JsonObject.class);
                    accessToken = tokenResponse.getString("access_token");
                    expiresIn = Instant.now().plus(tokenResponse.getInt("expires_in"), ChronoUnit.MINUTES);

                    refreshToken = tokenResponse.getString("refresh_token");
                    refreshExpiresIn = Instant.now().plus(tokenResponse.getInt("refresh_expires_in"), ChronoUnit.MINUTES);
                }
            }
        }
        return accessToken != null ? accessToken : "";
    }

    public String getHTTPBearerAuth() {
        return String.format("Bearer %s", getAccessToken());
    }

    public String getHTTPBasicAuth() {
        String clientId = System.getenv("SEC_SSO_OIDC_CLIENTID");
        String clientSecret = System.getenv("SEC_SSO_OIDC_CLIENTSECRET");
        String encodedCredentials = new String(Base64.getEncoder().encode(
                String.format("%s:%s", clientId, clientSecret).getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);

        LOGGER.info(String.format("Encoded Credentials: %s", encodedCredentials));

        return String.format("Basic %s", encodedCredentials);
    }

    public URI getServerBaseUri() {
        return URI.create(System.getProperty("SEC_SSO_REDIRECTTORPHOSTANDPORT", "https://localhost:9443"));
    }

    public URI getApplicationBaseUri() {
        URI applicationBaseUri = getServerBaseUri().resolve(CONTEXT_ROOT + (CONTEXT_ROOT.endsWith("/") ? "" : "/"));
        applicationBaseUri = applicationBaseUri.resolve(APPLICATION_PATH.startsWith("/") ?
                APPLICATION_PATH.substring(1) : APPLICATION_PATH);

        return applicationBaseUri;
    }

    /**
     *
     * <p>
     * This creates a MicroProfile REST Client proxy when
     * <pre>
     * @TestClient
     * public ServiceInterface serviceInterface;
     * </pre>
     *
     * This emulates the MicroProfile REST Client CDI injection:
     * <pre>
     * @Inject
     * @RestClient
     * private ServiceInterface serviceInterface;
     * </pre>
     *
     * @param interfaceClass
     * @param <T>
     * @return
     */
    public <T> T createTestClient(Class<T> interfaceClass) {

        RestClientBuilder clientBuilder = RestClientBuilder.newBuilder()
                .baseUri(getApplicationBaseUri())
                .sslContext(getSslContext())
                .hostnameVerifier((((hostname, session) -> true)))
                .register(JAXRSClientRequestTracer.class, Integer.MAX_VALUE)
                .register(TestAuthProvider.class, Priorities.AUTHENTICATION);

        if (!getAccessToken().isEmpty()) {
            Optional.ofNullable(getAccessToken()).map(provider -> this).ifPresent(clientBuilder::register);
        }

        return clientBuilder.build(interfaceClass);
    }

    /**
     * Callback that is invoked once <em>before</em> all tests in the current
     * container.
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {

    }

    /**
     * Callback that is invoked <em>before</em> an individual test and any
     * user-defined setup methods for that test have been executed.
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        List<Object> testInstances = context.getRequiredTestInstances().getAllInstances();

        // Initialize any field annotacted with @TestClient...
        for (Object testInstance : testInstances) {
            for (Field field : testInstance.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(TestClient.class)) {
                    field.set(testInstance, createTestClient(field.getType()));
                }
            }
        }
    }

    /**
     * Close underlying resources.
     *
     * @throws Throwable any throwable will be caught and rethrown
     */
    @Override
    public void close() throws Throwable {

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
