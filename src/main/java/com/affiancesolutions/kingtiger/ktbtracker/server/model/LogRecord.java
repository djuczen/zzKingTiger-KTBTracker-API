package com.affiancesolutions.kingtiger.ktbtracker.server.model;

import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LogRecord implements Supplier<String>, Serializable {

    private static final long serialVersionUID = -4319082313297214623L;

    private Instant timestamp;

    private int level;

    private String fileName;

    private int lineNumber;

    private int columnNumber;

    private String message;

    private List<String> additional = new ArrayList<>();

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getAdditional() {
        return additional;
    }

    public void setAdditional(List<String> additional) {
        this.additional = additional;
    }

    /**
     * Gets a result.
     *
     * @return a result
     */
    @Override
    public String get() {
        return String.format("%s %s [%s:%s:%s] %s",
                getTimestamp(),
                LogRecordLevel.values()[getLevel()].name(),
                getFileName(), getLineNumber(), getColumnNumber(),
                getMessage());
    }
}
