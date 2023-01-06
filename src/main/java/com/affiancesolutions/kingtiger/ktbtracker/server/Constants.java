package com.affiancesolutions.kingtiger.ktbtracker.server;

public interface Constants {

    String JAXRS_REQUEST_TRACER = "JAXRSRequestTracer";

    String HEADER_API_QUERY_COUNT = "X-API-Query-Count";
    String HEADER_API_QUERY_TIME = "X-API-Query-Time";
    String HEADER_API_TIME = "X-API-Time";

    String PARAM_CAN_ID = "can_id";
    String PARAM_CANDIDATE = "candidate";
    String PARAM_CURRENT = "current";
    String PARAM_CYCLE_ID = "cycle";
    String PARAM_FROM_DATE = "from_date";
    String PARAM_GROUP_NAME = "group";
    String PARAM_ME = "me";
    String PARAM_SCOPE = "scope";
    String PARAM_TO_DATE = "to_date";
    String PARAM_TRACKING_DATE = "tracking_date";
    String PARAM_TRACKING_ID = "tracking_id";
    String PARAM_USERID = "userid";
    String PARAM_WEEK = "week";

    String ROLE_ADMIN = "admin";
    String ROLE_CANDIDATE = "candidate";

    String SCOPE_ALL = "all";
    String SCOPE_CLASSES = "classes";
    String SCOPE_OTHER = "other";
    String SCOPE_PHYSICAL = "physical";

    final String ZERO = "0";

    String ALL_AUTHENTICATED = "**";

    String JWT_AUDIENCE = "ktbtracker-1659484356633";
    String JWT_ISSUER = "https://securetoken.google.com/ktbtracker-1659484356633";
}
