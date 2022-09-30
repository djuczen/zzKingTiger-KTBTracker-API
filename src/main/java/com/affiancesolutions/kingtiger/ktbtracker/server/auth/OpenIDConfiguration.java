package com.affiancesolutions.kingtiger.ktbtracker.server.auth;

import jakarta.json.bind.annotation.JsonbProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OpenIDConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonbProperty("issuer")
    private String issuer;

    @JsonbProperty("authorization_endpoint")
    private String authorizationEndpoint;

    @JsonbProperty("token_endpoint")
    private String tokenEndpoint;

    @JsonbProperty("introspection_endpoint")
    private String introspectionEndpoint;

    @JsonbProperty("useerinfo_endpoint")
    private String userinfoEndpoint;

    @JsonbProperty("end_session_endpoint")
    private String endSessionEndpoint;

    @JsonbProperty("jwks_uri")
    private String jwksUri;

    @JsonbProperty("check_session_iframe")
    private String checkSessionIframe;

    @JsonbProperty("grant_types_supported")
    private List<String> grantTypesSupported = new ArrayList<>();

    @JsonbProperty("response_types_supported")
    private List<String> responseTypesSupported = new ArrayList<>();

    @JsonbProperty("subject_types_supported")
    private List<String> subjectTypesSupported = new ArrayList<>();

    @JsonbProperty("id_token_signing_alg_values_supported")
    private List<String> idTokenSigningAlgValuesSupported = new ArrayList<>();

    @JsonbProperty("id_tiken_encryption_alg_values_supported")
    private List<String> idTokenEncryptionAlgValuesSupported = new ArrayList<>();

    @JsonbProperty("id_token_encryption_enc_values_supported")
    private List<String> idTokenEncryptionEncValuesSupported = new ArrayList<>();

    @JsonbProperty("userinfo_signing_alg_values_supported")
    private List<String> userinfoSigningAlgValuesSupported = new ArrayList<>();

    @JsonbProperty("request_object_signing_alg_values_supported")
    private List<String> requestObjectSigningAlgValuesSupported = new ArrayList<>();

    @JsonbProperty("response_modes_supported")
    private List<String> responseModesSupported = new ArrayList<>();

    @JsonbProperty("registration_endpoint")
    private String registrationEndpoint;

    @JsonbProperty("token_endpoint_auth_methods_supported")
    private List<String> tokenEndpointAuthMethodsSupported = new ArrayList<>();

    @JsonbProperty("token_endpoint_athh_signing_alg_values_supported")
    private List<String> tokenEndpointAuthSigningAlgValuesSupported = new ArrayList<>();

    @JsonbProperty("claims_supported")
    private List<String> claimsSupported = new ArrayList<>();

    @JsonbProperty("claim_types_supported")
    private List<String> claimTypesSupported = new ArrayList<>();

    @JsonbProperty("claim_parameter_supported")
    private boolean claimParameterSupported;

    @JsonbProperty("scopes_supported")
    private List<String> scopesSupported = new ArrayList<>();

    @JsonbProperty("request_parameter_supported")
    private boolean requestParameterSupported;

    @JsonbProperty("request_uri_parameter_supported")
    private boolean requestUriParameterSupported;

    @JsonbProperty("require_request_uri_registration")
    private boolean requireRequestUriRegistration;

    @JsonbProperty("code_challenge_methods_supported")
    private List<String> codeChallengeMethodsSupported = new ArrayList<>();

    @JsonbProperty("tls_client_certicate_bound_access_tokens")
    private boolean tlsClientCertificateBoundAccessTokens;

    @JsonbProperty("revocation_endpoint")
    private String revocationEndpoint;

    @JsonbProperty("revocation_endpoint_auth_methods_supported")
    private List<String> revocationEndpointAuthMethodsSupported = new ArrayList<>();

    @JsonbProperty("revocation_endpoint_auth_signing_alg_values_supported")
    private List<String> revocationEndpointAuthSigningAlgValuesSupported = new ArrayList<>();

    @JsonbProperty("backchannel_logout_supported")
    private boolean backchannelLogoutSupported;

    @JsonbProperty("backchannel_logout_session_supported")
    private boolean backchannelLogoutSessionSupported;


    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAuthorizationEndpoint() {
        return authorizationEndpoint;
    }

    public void setAuthorizationEndpoint(String authorizationEndpoint) {
        this.authorizationEndpoint = authorizationEndpoint;
    }

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    public String getIntrospectionEndpoint() {
        return introspectionEndpoint;
    }

    public void setIntrospectionEndpoint(String introspectionEndpoint) {
        this.introspectionEndpoint = introspectionEndpoint;
    }

    public String getUserinfoEndpoint() {
        return userinfoEndpoint;
    }

    public void setUserinfoEndpoint(String userinfoEndpoint) {
        this.userinfoEndpoint = userinfoEndpoint;
    }

    public String getEndSessionEndpoint() {
        return endSessionEndpoint;
    }

    public void setEndSessionEndpoint(String endSessionEndpoint) {
        this.endSessionEndpoint = endSessionEndpoint;
    }

    public String getJwksUri() {
        return jwksUri;
    }

    public void setJwksUri(String jwksUri) {
        this.jwksUri = jwksUri;
    }

    public String getCheckSessionIframe() {
        return checkSessionIframe;
    }

    public void setCheckSessionIframe(String checkSessionIframe) {
        this.checkSessionIframe = checkSessionIframe;
    }

    public List<String> getGrantTypesSupported() {
        return grantTypesSupported;
    }

    public void setGrantTypesSupported(List<String> grantTypesSupported) {
        this.grantTypesSupported = grantTypesSupported;
    }

    public List<String> getResponseTypesSupported() {
        return responseTypesSupported;
    }

    public void setResponseTypesSupported(List<String> responseTypesSupported) {
        this.responseTypesSupported = responseTypesSupported;
    }

    public List<String> getSubjectTypesSupported() {
        return subjectTypesSupported;
    }

    public void setSubjectTypesSupported(List<String> subjectTypesSupported) {
        this.subjectTypesSupported = subjectTypesSupported;
    }

    public List<String> getIdTokenSigningAlgValuesSupported() {
        return idTokenSigningAlgValuesSupported;
    }

    public void setIdTokenSigningAlgValuesSupported(List<String> idTokenSigningAlgValuesSupported) {
        this.idTokenSigningAlgValuesSupported = idTokenSigningAlgValuesSupported;
    }

    public List<String> getIdTokenEncryptionAlgValuesSupported() {
        return idTokenEncryptionAlgValuesSupported;
    }

    public void setIdTokenEncryptionAlgValuesSupported(List<String> idTokenEncryptionAlgValuesSupported) {
        this.idTokenEncryptionAlgValuesSupported = idTokenEncryptionAlgValuesSupported;
    }

    public List<String> getIdTokenEncryptionEncValuesSupported() {
        return idTokenEncryptionEncValuesSupported;
    }

    public void setIdTokenEncryptionEncValuesSupported(List<String> idTokenEncryptionEncValuesSupported) {
        this.idTokenEncryptionEncValuesSupported = idTokenEncryptionEncValuesSupported;
    }

    public List<String> getUserinfoSigningAlgValuesSupported() {
        return userinfoSigningAlgValuesSupported;
    }

    public void setUserinfoSigningAlgValuesSupported(List<String> userinfoSigningAlgValuesSupported) {
        this.userinfoSigningAlgValuesSupported = userinfoSigningAlgValuesSupported;
    }

    public List<String> getRequestObjectSigningAlgValuesSupported() {
        return requestObjectSigningAlgValuesSupported;
    }

    public void setRequestObjectSigningAlgValuesSupported(List<String> requestObjectSigningAlgValuesSupported) {
        this.requestObjectSigningAlgValuesSupported = requestObjectSigningAlgValuesSupported;
    }

    public List<String> getResponseModesSupported() {
        return responseModesSupported;
    }

    public void setResponseModesSupported(List<String> responseModesSupported) {
        this.responseModesSupported = responseModesSupported;
    }

    public String getRegistrationEndpoint() {
        return registrationEndpoint;
    }

    public void setRegistrationEndpoint(String registrationEndpoint) {
        this.registrationEndpoint = registrationEndpoint;
    }

    public List<String> getTokenEndpointAuthMethodsSupported() {
        return tokenEndpointAuthMethodsSupported;
    }

    public void setTokenEndpointAuthMethodsSupported(List<String> tokenEndpointAuthMethodsSupported) {
        this.tokenEndpointAuthMethodsSupported = tokenEndpointAuthMethodsSupported;
    }

    public List<String> getTokenEndpointAuthSigningAlgValuesSupported() {
        return tokenEndpointAuthSigningAlgValuesSupported;
    }

    public void setTokenEndpointAuthSigningAlgValuesSupported(List<String> tokenEndpointAuthSigningAlgValuesSupported) {
        this.tokenEndpointAuthSigningAlgValuesSupported = tokenEndpointAuthSigningAlgValuesSupported;
    }

    public List<String> getClaimsSupported() {
        return claimsSupported;
    }

    public void setClaimsSupported(List<String> claimsSupported) {
        this.claimsSupported = claimsSupported;
    }

    public List<String> getClaimTypesSupported() {
        return claimTypesSupported;
    }

    public void setClaimTypesSupported(List<String> claimTypesSupported) {
        this.claimTypesSupported = claimTypesSupported;
    }

    public boolean isClaimParameterSupported() {
        return claimParameterSupported;
    }

    public void setClaimParameterSupported(boolean claimParameterSupported) {
        this.claimParameterSupported = claimParameterSupported;
    }

    public List<String> getScopesSupported() {
        return scopesSupported;
    }

    public void setScopesSupported(List<String> scopesSupported) {
        this.scopesSupported = scopesSupported;
    }

    public boolean isRequestParameterSupported() {
        return requestParameterSupported;
    }

    public void setRequestParameterSupported(boolean requestParameterSupported) {
        this.requestParameterSupported = requestParameterSupported;
    }

    public boolean isRequestUriParameterSupported() {
        return requestUriParameterSupported;
    }

    public void setRequestUriParameterSupported(boolean requestUriParameterSupported) {
        this.requestUriParameterSupported = requestUriParameterSupported;
    }

    public boolean isRequireRequestUriRegistration() {
        return requireRequestUriRegistration;
    }

    public void setRequireRequestUriRegistration(boolean requireRequestUriRegistration) {
        this.requireRequestUriRegistration = requireRequestUriRegistration;
    }

    public List<String> getCodeChallengeMethodsSupported() {
        return codeChallengeMethodsSupported;
    }

    public void setCodeChallengeMethodsSupported(List<String> codeChallengeMethodsSupported) {
        this.codeChallengeMethodsSupported = codeChallengeMethodsSupported;
    }

    public boolean isTlsClientCertificateBoundAccessTokens() {
        return tlsClientCertificateBoundAccessTokens;
    }

    public void setTlsClientCertificateBoundAccessTokens(boolean tlsClientCertificateBoundAccessTokens) {
        this.tlsClientCertificateBoundAccessTokens = tlsClientCertificateBoundAccessTokens;
    }

    public String getRevocationEndpoint() {
        return revocationEndpoint;
    }

    public void setRevocationEndpoint(String revocationEndpoint) {
        this.revocationEndpoint = revocationEndpoint;
    }

    public List<String> getRevocationEndpointAuthMethodsSupported() {
        return revocationEndpointAuthMethodsSupported;
    }

    public void setRevocationEndpointAuthMethodsSupported(List<String> revocationEndpointAuthMethodsSupported) {
        this.revocationEndpointAuthMethodsSupported = revocationEndpointAuthMethodsSupported;
    }

    public List<String> getRevocationEndpointAuthSigningAlgValuesSupported() {
        return revocationEndpointAuthSigningAlgValuesSupported;
    }

    public void setRevocationEndpointAuthSigningAlgValuesSupported(List<String> revocationEndpointAuthSigningAlgValuesSupported) {
        this.revocationEndpointAuthSigningAlgValuesSupported = revocationEndpointAuthSigningAlgValuesSupported;
    }

    public boolean isBackchannelLogoutSupported() {
        return backchannelLogoutSupported;
    }

    public void setBackchannelLogoutSupported(boolean backchannelLogoutSupported) {
        this.backchannelLogoutSupported = backchannelLogoutSupported;
    }

    public boolean isBackchannelLogoutSessionSupported() {
        return backchannelLogoutSessionSupported;
    }

    public void setBackchannelLogoutSessionSupported(boolean backchannelLogoutSessionSupported) {
        this.backchannelLogoutSessionSupported = backchannelLogoutSessionSupported;
    }
}
