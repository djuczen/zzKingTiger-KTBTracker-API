package com.affiancesolutions.kingtiger.ktbtracker.server.auth;

import com.affiancesolutions.kingtiger.commons.logging.JAXRSClientRequestTracer;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public final class OIDCUtils {

    private static final String OIDC_DISCOVERYENDPOINT = System.getenv("SEC_SSO_OIDC_DISCOVERYENDPOINT");

    private static OpenIDConfiguration openIDConfiguration = new OpenIDConfiguration();

    static {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
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

            Client client = ClientBuilder.newBuilder()
                    .sslContext(sslContext)
                    .hostnameVerifier(((hostname, session) -> true))
                    .register(JAXRSClientRequestTracer.class, Integer.MAX_VALUE)
                    .build();

            Invocation.Builder builder = client
                    .target(OIDC_DISCOVERYENDPOINT)
                    .request(MediaType.APPLICATION_JSON_TYPE);

            Response response = builder.get();

            openIDConfiguration = response.readEntity(OpenIDConfiguration.class);

        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public OpenIDConfiguration getOpenIDConfiguration() {
        return openIDConfiguration;
    }


    public static String getJwksUri() {
        return openIDConfiguration.getJwksUri();
    }

    public static String getIssuer() {
        return openIDConfiguration.getIssuer();
    }

    public static String getAuthorizationEndpoint() {
        return openIDConfiguration.getAuthorizationEndpoint();
    }

    public static String getTokenEndpoint() {
        return openIDConfiguration.getTokenEndpoint();
    }
}
