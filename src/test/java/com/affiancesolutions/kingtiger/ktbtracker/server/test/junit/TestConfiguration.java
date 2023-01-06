package com.affiancesolutions.kingtiger.ktbtracker.server.test.junit;

import com.affiancesolutions.kingtiger.commons.logging.JAXRSClientRequestTracer;
import com.affiancesolutions.kingtiger.ktbtracker.server.KTBTrackerApplication;
import com.affiancesolutions.kingtiger.ktbtracker.server.test.health.HealthCheckService;
import jakarta.json.JsonObject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class TestConfiguration implements BeforeAllCallback, BeforeEachCallback, ExtensionContext.Store.CloseableResource {

    private static final String CLASS_NAME = TestConfiguration.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private static final URL APPLICATION_URL =
            ConfigProvider.getConfig().getValue("kingtiger.ktbtracker.svc.service/mp-rest/url", URL.class);

    private static final String APPLICATION_PATH = KTBTrackerApplication.class.getAnnotation(ApplicationPath.class).value();

    private static final String CLIENT_ID = ConfigProvider.getConfig().getValue("SEC_SSO_OIDC_CLIENTID", String.class);

    private static final String CLIENT_SECRET = ConfigProvider.getConfig().getValue("SEC_SSO_OIDC_CLIENTSECRET", String.class);

    private static final String CONTEXT_ROOT = "/";

    private static final String OIDC_DISCOVERYENDPOINT = "";

    private static final String OAUTH2_TOKENENDPOINT = System.getenv("SEC_SSO_OAUTH2_TOKENENDPOINT");

    private static final Lock LOCK = new ReentrantLock();

    private static volatile boolean started;

    private static SSLContext sslContext;

    private static Client client;

    private static volatile String idToken;

    private static String accessToken;

    private static volatile Instant expiresIn;

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

    public static String getIdToken() {
        final String METHOD_NAME = "getIdToken";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        if (idToken == null || Instant.now().isAfter(expiresIn)) {
            LOGGER.finer("Reentrant lock for idToken obtained.");
            LOCK.lock();
            try {
                if (idToken == null || Instant.now().isAfter(expiresIn)) {
                    Form tokenForm = new Form();
                    tokenForm.param("email", CLIENT_ID);
                    tokenForm.param("password", CLIENT_SECRET);

                    Invocation.Builder builder = getClient()
                            .target(getApplicationBaseUri().resolve("api/auth/signInWithPassword"))
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
                LOGGER.finer("Reentrant lock for idToken released.");
            }
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
        return idToken != null ? idToken : "";
    }

    public static String getHTTPBearerAuth() {
        return String.format("Bearer %s", getIdToken());
    }

    public static String getHTTPBasicAuth() {
        return String.format("Basic %s", Base64.getEncoder().encodeToString(
                String.format("%s:%s", CLIENT_ID, CLIENT_SECRET).getBytes(StandardCharsets.UTF_8)));
    }

    public static URI getServerBaseUri() {
        return getApplicationBaseUri().resolve("/");
    }

    public static URI getApplicationBaseUri() {
        return URI.create(APPLICATION_URL.toString());
    }

    /**
     * Callback that is invoked once <em>before</em> all tests in the current
     * container.
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        final String METHOD_NAME = "beforeAll";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, context);

        LOCK.lock();
        try {
            if (idToken == null || Instant.now().isAfter(expiresIn)) {
                LOGGER.finer("Saving idToken/expiresIn in GLOBAL store.");
                getIdToken();
                context.getRoot().getStore(GLOBAL).put("idToken", idToken);
                context.getRoot().getStore(GLOBAL).put("expiresIn", expiresIn);
            }
        } finally {
            LOCK.unlock();
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }

    /**
     * Callback that is invoked <em>before</em> an individual test and any
     * user-defined setup methods for that test have been executed.
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        final String METHOD_NAME = "beforeEach";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, context);
        List<Object> testInstances = context.getRequiredTestInstances().getAllInstances();
        //context.getRoot().getStore(GLOBAL).get("x", String.class);

        LOCK.lock();
        try {
            LOGGER.finer("Restoring idToken/expiresIn from GLOBAL store.");
            idToken = context.getRoot().getStore(GLOBAL).get("idToken", String.class);
            expiresIn = context.getRoot().getStore(GLOBAL).get("expiresIn", Instant.class);
        } finally {
            LOCK.unlock();
        }

        // Initialize any field annotated with @TestClient...
        for (Object testInstance : testInstances) {
            for (Field field : testInstance.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(TestClient.class)) {
                    field.set(testInstance, createTestClient(field.getType()));
                }
            }
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
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
    private <T> T createTestClient(Class<T> interfaceClass) {

        RestClientBuilder clientBuilder = RestClientBuilder.newBuilder()
                .sslContext(getSslContext())
                .hostnameVerifier((((hostname, session) -> true)))
                .register(JAXRSClientRequestTracer.class, Integer.MAX_VALUE);

        if (HealthCheckService.class.getSimpleName().equals(interfaceClass.getSimpleName())) {
            clientBuilder.baseUri(getServerBaseUri());
        } else {
            clientBuilder.baseUri(getApplicationBaseUri())
                    .register(TestAuthProvider.class, Priorities.AUTHENTICATION);
        }

        return clientBuilder.build(interfaceClass);
    }
}
