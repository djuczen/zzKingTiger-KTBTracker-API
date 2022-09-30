package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import java.io.Serializable;

public class ErrorResult implements Serializable {

    private static final long serialVersionUID = 3243677369975441088L;

    private ErrorStatus error;

    public ErrorResult(ErrorStatus error) {
        this.error = error;
    }

    public ErrorStatus getError() {
        return error;
    }

    public void setError(ErrorStatus error) {
        this.error = error;
    }
}
