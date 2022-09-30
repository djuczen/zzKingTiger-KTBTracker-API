package com.affiancesolutions.kingtiger.ktbtracker.server;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.IOException;
import java.util.logging.Logger;

@WebListener
public class KTBTrackerContextListener implements ServletContextListener {

    private static final String CLASS_NAME = KTBTrackerContextListener.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        final String METHOD_NAME = "contextInitialized";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, servletContextEvent);

        try {
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .build();
            FirebaseApp.initializeApp(firebaseOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
    }
}
