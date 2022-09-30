package com.affiancesolutions.kingtiger.ktbtracker.server.restapi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;

@ApplicationScoped
@LoginConfig(authMethod = "MP-JWT", realmName = "MP-JWT")
@ApplicationPath("/api")
public class KTBTrackerApplication extends Application {
}
