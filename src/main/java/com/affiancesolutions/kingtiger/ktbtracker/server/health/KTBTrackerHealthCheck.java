package com.affiancesolutions.kingtiger.ktbtracker.server.health;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.health.*;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.logging.Logger;


@ApplicationScoped
public class KTBTrackerHealthCheck {

    private static final String CLASS_NAME = KTBTrackerHealthCheck.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Resource
    private DataSource ds;


    @Produces
    @Liveness
    public HealthCheck livenessCheck() {
        final String METHOD_NAME = "livenessCheck";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);
        HealthCheckResponseBuilder liveness = HealthCheckResponse.named(String.format("%s.%s",
                KTBTrackerHealthCheck.class.getSimpleName(), METHOD_NAME))
                .up();

        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

        liveness = liveness
                .withData("cpuUsage", String.valueOf(operatingSystemMXBean.getSystemLoadAverage()))
                .withData("usedHeap", memoryMXBean.getHeapMemoryUsage().getUsed())
                .withData("maxHeap", memoryMXBean.getHeapMemoryUsage().getMax())
                .withData("jvmStartTime", runtimeMXBean.getStartTime())
                .withData("jvmUpTime", runtimeMXBean.getUptime())
                .up();

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, liveness.build());
        return liveness::build;
    }

    @Produces
    @Readiness
    public HealthCheck readinessCheck() {
        final String METHOD_NAME = "readinessCheck";
        LOGGER.entering(CLASS_NAME, METHOD_NAME);
        HealthCheckResponseBuilder readiness = HealthCheckResponse.named(String.format("%s.%s",
                        KTBTrackerHealthCheck.class.getSimpleName(), METHOD_NAME))
                .up();

        try (Connection conn = ds.getConnection()) {
            if (conn != null) {
                DatabaseMetaData databaseMetaData = conn.getMetaData();

                readiness = readiness
                        .withData("databaseProductName", databaseMetaData.getDatabaseProductName())
                        .withData("databaseProductVersion", databaseMetaData.getDatabaseProductVersion())
                        .withData("driverName", databaseMetaData.getDriverName())
                        .withData("driverVersion", databaseMetaData.getDriverVersion())
                        .withData("url", databaseMetaData.getURL())
                        .up();
            } else {
                readiness = readiness
                        .withData("errorCode", 0)
                        .withData("sqlState", "N/A")
                        .withData("exceptionMessage", "Database connection unavailable (null).")
                        .down();
            }
        } catch (SQLException se) {
            LOGGER.throwing(CLASS_NAME, METHOD_NAME, se);

            readiness = readiness
                    .withData("errorCode", se.getErrorCode())
                    .withData("sqlState", se.getSQLState())
                    .withData("exceptionMessage", se.getLocalizedMessage())
                    .down();
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME, readiness.build());
        return readiness::build;
    }
}
