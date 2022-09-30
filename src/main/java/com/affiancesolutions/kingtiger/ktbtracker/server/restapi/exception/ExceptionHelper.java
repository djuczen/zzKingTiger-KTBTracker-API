package com.affiancesolutions.kingtiger.ktbtracker.server.restapi.exception;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public final class ExceptionHelper {

    public static JsonObject toEntity(Throwable t) {
        return toEntity(t, 500);
    }

    public static JsonObject toEntity(Throwable t, int status) {
        return Json.createObjectBuilder().add("status", status).add("msg", t.getLocalizedMessage()).build();
    }
}
