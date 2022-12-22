package com.affiancesolutions.kingtiger.ktbtracker.server.model;

public enum LogRecordLevel {
    TRACE, DEBUG, INFO, LOG, WARN, ERROR, FATAL, OFF;

    public String getLevel() {
        switch (this) {
            case TRACE:
                return "TRACE";
            case DEBUG:
                return "DEBUG";
            case INFO:
                return "INFO";
            case LOG:
                return "LOG";
            case WARN:
                return "WARN";
            case ERROR:
                return "ERROR";
            case FATAL:
                return "FATAL";
            case OFF:
                return "OFF";
            default:
                return "UNKNOWN";
        }
    }
}
