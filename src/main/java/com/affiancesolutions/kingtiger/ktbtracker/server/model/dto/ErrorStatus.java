package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import jakarta.json.bind.annotation.JsonbTransient;

public class ErrorStatus {

    private int code;

    private String message;

    @JsonbTransient
    private Throwable cause;

    public ErrorStatus(int code) {
        this.code = code;
        this.message = "";
    }

    public ErrorStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorStatus(String message) {
        this.code = 500;
        this.message = message;
    }

    public ErrorStatus(int code, Throwable cause) {
        this.code = code;
        this.message = getRootCause(cause).getLocalizedMessage();
    }

    public ErrorStatus(Throwable cause) {
        this.code = 500;
        this.message = getRootCause(cause).getLocalizedMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    private Throwable getRootCause(Throwable t) {
        if (t.getCause() != null) {
            return getRootCause(t.getCause());
        }

        return t;
    }
}
