package com.affiancesolutions.kingtiger.ktbtracker.server.auth.impl;


import com.affiancesolutions.kingtiger.ktbtracker.server.auth.OIDCUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;

import javax.security.auth.Subject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.util.*;
import java.util.logging.Logger;

import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.JWT_AUDIENCE;
import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.JWT_ISSUER;

public class MicroProfileJsonWebTokenImpl implements JsonWebToken {

    private static final String CLASS_NAME = MicroProfileJsonWebTokenImpl.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private static final String RAW_TOKEN = "rawToken";

    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] {
            new ObjectStreamField(RAW_TOKEN, String.class),
    };

    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private volatile FirebaseToken firebaseToken = null;

    private static final JwtClaims NOT_SET = new JwtClaims();

    private volatile JwtClaims claimsSet = NOT_SET;

    private transient String rawToken;


    public MicroProfileJsonWebTokenImpl(final String rawToken) throws InvalidJwtException, FirebaseAuthException {
        final String METHOD_NAME = "<init>";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, abbrev(rawToken));

        firebaseToken = firebaseAuth.verifyIdToken(rawToken);
        LOGGER.finest(String.format("FirebaseToken: %s", firebaseToken.getClaims()));
        this.rawToken = rawToken;
        getJwtClaims();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, this);
    }

    /**
     * Returns the unique name of this principal. This either comes from the upn claim, or if that is missing, the
     * preferred_username claim. Note that for guaranteed interoperability a upn claim should be used.
     *
     * @return the unique name of this principal.
     */
    @Override
    public String getName() {
        if (containsClaim(Claims.upn.name())) {
            return getClaim(Claims.upn);
        }
        if (containsClaim(Claims.preferred_username.name())) {
            return getClaim(Claims.preferred_username);
        }

        return getClaim(Claims.sub);
    }

    /**
     * Get the raw bearer token string originally passed in the authentication header
     *
     * @return raw bear token string
     */
    @Override
    public String getRawToken() {
        return rawToken;
    }

    /**
     * The iss(Issuer) claim identifies the principal that issued the JWT
     *
     * @return the iss claim.
     */
    @Override
    public String getIssuer() {
        return getClaim(Claims.iss);
    }

    /**
     * The aud(Audience) claim identifies the recipients that the JWT is intended for.
     *
     * @return the aud claim or null if the claim is not present
     */
    @Override
    public Set<String> getAudience() {
        Set<String> audienceSet = new HashSet<>();

        try {
            List<String> audienceList = getClaimsSet().getStringListClaimValue(Claims.aud.name());
            if (audienceList != null) {
                audienceSet.addAll(audienceList);
            }
        } catch (MalformedClaimException e) {
        }

        return audienceSet.isEmpty() ? null : audienceSet;
    }

    /**
     * The sub(Subject) claim identifies the principal that is the subject of the JWT. This is the token issuing IDP
     * subject.
     *
     * @return the sub claim.
     */
    @Override
    public String getSubject() {
        return getClaim(Claims.sub);
    }

    /**
     * The jti(JWT ID) claim provides a unique identifier for the JWT. The identifier value MUST be assigned in a manner
     * that ensures that there is a negligible probability that the same value will be accidentally assigned to a
     * different data object; if the application uses multiple issuers, collisions MUST be prevented among values
     * produced by different issuers as well. The "jti" claim can be used to prevent the JWT from being replayed.
     *
     * @return the jti claim.
     */
    @Override
    public String getTokenID() {
        return getClaim(Claims.jti);
    }

    /**
     * The exp (Expiration time) claim identifies the expiration time on or after which the JWT MUST NOT be accepted for
     * processing in seconds since 1970-01-01T00:00:00Z UTC
     *
     * @return the exp claim.
     */
    @Override
    public long getExpirationTime() {
        return getClaim(Claims.exp);
    }

    /**
     * The iat(Issued at time) claim identifies the time at which the JWT was issued in seconds since
     * 1970-01-01T00:00:00Z UTC
     *
     * @return the iat claim
     */
    @Override
    public long getIssuedAtTime() {
        return getClaim(Claims.iat);
    }

    /**
     * The groups claim provides the group names the JWT principal has been granted.
     * <p>
     * This is a MicroProfile specific claim.
     *
     * @return a possibly empty set of group names.
     */
    @Override
    public Set<String> getGroups() {
        Set<String> groups = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        try {
            if (containsClaim(Claims.groups.name())) {
                groups.addAll(getClaimsSet().getStringListClaimValue(Claims.groups.name()));
            }
        } catch (MalformedClaimException e) {
            e.printStackTrace();
        }

        return groups;
    }

    /**
     * Access the names of all claims are associated with this token.
     *
     * @return non-standard claim names in the token
     */
    @Override
    public Set<String> getClaimNames() {
        return (Set<String>) getClaimsSet().getClaimNames();
    }

    /**
     * Verify is a given claim exists
     *
     * @param claimName - the name of the claim
     * @return true if the JsonWebToken contains the claim, false otherwise
     */
    @Override
    public boolean containsClaim(String claimName) {
        return getClaimsSet().hasClaim(claimName);
    }

    /**
     * Access the value of the indicated claim.
     *
     * @param claimName - the name of the claim
     * @return the value of the indicated claim if it exists, null otherwise.
     */
    @Override
    public <T> T getClaim(String claimName) {
        Claims claimType = Claims.UNKNOWN;
        Object claim = null;
        try {
            claimType = Claims.valueOf(claimName);
        } catch (IllegalArgumentException e) {
        }
        switch (claimType) {
            case exp:
            case iat:
            case nbf:
            case auth_time:
            case updated_at:
                try {
                    claim = getClaimsSet().getClaimValue(claimName, Long.class);
                    if (claim == null) {
                        claim = Long.valueOf(0L);
                    }
                } catch (MalformedClaimException e) {
                }
                break;
            case aud:
                break;
            case groups:
                break;
            case UNKNOWN:
                claim = getClaimsSet().getClaimValue(claimName);
            default:
                claim = getClaimsSet().getClaimValue(claimType.name());
        }
        return (T) claim;
    }

    /**
     * Access the value of the indicated claim.
     *
     * @param claim - the claim
     * @return the value of the indicated claim if it exists, null otherwise.
     */
    @Override
    public <T> T getClaim(Claims claim) {
        return getClaim(claim.name());
    }

    /**
     * A utility method to access a claim value in an {@linkplain Optional} wrapper
     *
     * @param claimName - the name of the claim
     * @return an Optional wrapper of the claim value
     */
    @Override
    public <T> Optional<T> claim(String claimName) {
        return Optional.ofNullable(getClaim(claimName));
    }

    /**
     * A utility method to access a claim value in an {@linkplain Optional} wrapper
     *
     * @param claim - the claim
     * @return an Optional wrapper of the claim value
     */
    @Override
    public <T> Optional<T> claim(Claims claim) {
        return Optional.ofNullable(getClaim(claim.name()));
    }

    /**
     * Returns true if the specified subject is implied by this principal.
     *
     * @param subject the {@code Subject}
     * @return true if {@code subject} is non-null and is
     * implied by this principal, or false otherwise.
     * @implSpec The default implementation of this method returns true if
     * {@code subject} is non-null and contains at least one principal that
     * is equal to this principal.
     *
     * <p>Subclasses may override this with a different implementation, if
     * necessary.
     * @since 1.8
     */
    @Override
    public boolean implies(Subject subject) {
        return JsonWebToken.super.implies(subject);
    }

    @Override
    public MicroProfileJsonWebTokenImpl clone() {
        try {
            return new MicroProfileJsonWebTokenImpl(rawToken);
        } catch (InvalidJwtException | FirebaseAuthException e) {
        }
        return null;
    }

    @Override
    public String toString() {
        return String.valueOf(getClaimsSet().toJson());
    }

    private JwtClaims getClaimsSet() {
        if (claimsSet == NOT_SET) {
            synchronized (this) {
                if (claimsSet == NOT_SET) {
                    try {
                        claimsSet = getJwtClaims();
                    } catch (InvalidJwtException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return claimsSet;
    }

    private JwtClaims getJwtClaims() throws InvalidJwtException {
        final String METHOD_NAME = "getJwtClaims";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);

        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setSkipSignatureVerification()
                .setExpectedIssuer(JWT_ISSUER)
                .setExpectedAudience(JWT_AUDIENCE)
                .setRequireExpirationTime()
                .build();

        JwtClaims processed = jwtConsumer.processToClaims(rawToken);

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, processed);
        return processed;
    }

    private String abbrev(String value) {
        return value != null ? value.length() > 32 ? String.format("%s ... %s", value.substring(0, 16), value.substring(value.length() - 16)) : value : null;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField fields = in.readFields();

        rawToken = (String) fields.get(RAW_TOKEN, null);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        ObjectOutputStream.PutField fields = out.putFields();

        fields.put(RAW_TOKEN, rawToken);
    }
}
