package com.affiancesolutions.kingtiger.ktbtracker.server.restapi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;

import javax.annotation.security.RolesAllowed;

import static com.affiancesolutions.kingtiger.ktbtracker.server.Constants.ALL_AUTHENTICATED;

@ApplicationScoped
@LoginConfig(authMethod = "MP-JWT", realmName = "MP-JWT")
@ApplicationPath("/api")
public class KTBTrackerApplication extends Application {
}
