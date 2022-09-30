package com.affiancesolutions.kingtiger.ktbtracker.server.auth.impl;

import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.security.Principal;
import java.util.logging.Logger;

public class MicroProfileSecurityContextImpl implements SecurityContext {

    private static final String CLASS_NAME = MicroProfileSecurityContextImpl.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private final SecurityContext securityContext;

    private final JsonWebToken jsonWebToken;


    public MicroProfileSecurityContextImpl(SecurityContext securityContext, JsonWebToken jsonWebToken) {
        this.securityContext = securityContext;
        this.jsonWebToken = jsonWebToken;
    }

    /**
     * Returns a <code>java.security.Principal</code> object containing the name of the current authenticated user. If the
     * user has not been authenticated, the method returns null.
     *
     * @return a <code>java.security.Principal</code> containing the name of the user making this request; null if the user
     * has not been authenticated
     * @throws IllegalStateException if called outside the scope of a request
     */
    @Override
    public Principal getUserPrincipal() {
        return jsonWebToken != null ? jsonWebToken : securityContext.getUserPrincipal();
    }

    /**
     * Returns a boolean indicating whether the authenticated user is included in the specified logical "role". If the user
     * has not been authenticated, the method returns <code>false</code>.
     *
     * @param role a <code>String</code> specifying the name of the role
     * @return a <code>boolean</code> indicating whether the user making the request belongs to a given role;
     * <code>false</code> if the user has not been authenticated
     * @throws IllegalStateException if called outside the scope of a request
     */
    @Override
    public boolean isUserInRole(String role) {
        boolean userInRole = getUserPrincipal() != null && "**".equals(role);

        if (!userInRole && getUserPrincipal() != null) {
            if (getUserPrincipal() instanceof JsonWebToken) {
                userInRole = ((JsonWebToken) getUserPrincipal()).getGroups().contains(role);
            } else {
                userInRole = securityContext.isUserInRole(role);
            }
        }

        return userInRole;
    }

    /**
     * Returns a boolean indicating whether this request was made using a secure channel, such as HTTPS.
     *
     * @return <code>true</code> if the request was made using a secure channel, <code>false</code> otherwise
     * @throws IllegalStateException if called outside the scope of a request
     */
    @Override
    public boolean isSecure() {
        return securityContext.isSecure();
    }

    /**
     * Returns the string value of the authentication scheme used to protect the resource. If the resource is not
     * authenticated, null is returned.
     * <p>
     * Values are the same as the CGI variable AUTH_TYPE
     *
     * @return one of the static members BASIC_AUTH, FORM_AUTH, CLIENT_CERT_AUTH, DIGEST_AUTH (suitable for == comparison)
     * or the container-specific string indicating the authentication scheme, or null if the request was not authenticated.
     * @throws IllegalStateException if called outside the scope of a request
     */
    @Override
    public String getAuthenticationScheme() {
        return securityContext.getAuthenticationScheme();
    }
}

