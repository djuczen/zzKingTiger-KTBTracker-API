package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.resources;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.LogRecord;
import com.affiancesolutions.kingtiger.ktbtracker.server.model.LogRecordLevel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import java.util.logging.Logger;

@RequestScoped
@Path("/logs")
public class LoggingResource {

    private static final String CLASS_NAME = LoggingResource.class.getName();

    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private static final Logger LOG = Logger.getLogger("UILogger");

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response recordLogRecord(
            LogRecord logRecord
    ) {
        final String METHOD_NAME = "recordLogRecord";
        LOGGER.entering(CLASS_NAME, METHOD_NAME, logRecord);

        switch (LogRecordLevel.values()[logRecord.getLevel()]) {
            case TRACE:
                LOG.finest(logRecord);
                break;
            case DEBUG:
                LOG.finer(logRecord);
                break;
            case LOG:
                LOG.fine(logRecord);
                break;
            case INFO:
                LOG.info(logRecord);
                break;
            case WARN:
                LOG.warning(logRecord);
                break;
            case ERROR:
            case FATAL:
                LOG.severe(logRecord);
                break;
            case OFF:
                break;
            default:
                LOG.config(logRecord);
        }

        LOGGER.exiting(CLASS_NAME, METHOD_NAME);
        return Response.created(null).build();
    }
}
