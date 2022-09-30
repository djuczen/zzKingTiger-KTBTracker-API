package com.affiancesolutions.kingtiger.ktbtracker.server.auth;

import com.ibm.websphere.security.WebTrustAssociationException;
import com.ibm.websphere.security.WebTrustAssociationFailedException;
import com.ibm.wsspi.security.tai.TAIResult;
import com.ibm.wsspi.security.tai.TrustAssociationInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
import java.util.logging.Logger;

public class JsonWebTokenTAI implements TrustAssociationInterceptor {

    private static final String CLASS_NAME = JsonWebTokenTAI.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Override
    public boolean isTargetInterceptor(HttpServletRequest request) throws WebTrustAssociationException {
        boolean match = false;

        if (request.getHeader("Authorization") != null) {
            LOGGER.fine(String.format("Authorization: %s", request.getHeader("Authorization")));
            match = request.getHeader("Authorization").matches("Bearer ([a-zA-Z0-9]|-|_)+\\.([a-zA-Z0-9]|-|_)+\\.([a-zA-Z0-9]|-|_|=)+");
            LOGGER.fine(String.format("Authorization matches: %s", match));
        }

        return match;
    }

    @Override
    public TAIResult negotiateValidateandEstablishTrust(HttpServletRequest request, HttpServletResponse response) throws WebTrustAssociationFailedException {
        final String METHOD_NAME = "negotiateValidateandEstablishTrust";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, new Object[]{request, response});
        String rawToken = request.getHeader("Authorization").split(" ")[1];
        String taiUser = "";


        LOGGER.exiting(METHOD_NAME, CLASS_NAME, taiUser);
        return TAIResult.create(HttpServletResponse.SC_OK, taiUser);
    }

    @Override
    public int initialize(Properties properties) throws WebTrustAssociationFailedException {
        return 0;
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getType() {
        return CLASS_NAME;
    }

    @Override
    public void cleanup() {

    }
}
