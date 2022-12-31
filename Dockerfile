FROM openliberty/open-liberty:full-java17-openj9-ubi

ARG VERBOSE=true
ARG TLS=true

ENV SEC_IMPORT_K8S_CERTS=true

# Add Liberty server configuration including all necessary features
COPY --chown=1001:0  build/wlp/usr/servers/defaultServer/configDropins/ /config/configDropins/
COPY --chown=1001:0  build/wlp/usr/servers/defaultServer/jvm.options /config/
COPY --chown=1001:0  build/wlp/usr/servers/defaultServer/server.env /config/
COPY --chown=1001:0  build/wlp/usr/servers/defaultServer/server.xml /config/

COPY --chown=1001:0  build/wlp/usr/shared/ /liberty/usr/shared/

# Add application WAR
COPY --chown=1001:0  build/libs/KingTiger-KTBTracker-API-1.0.0.war /config/dropins/

# This script will add the requested server configurations, apply any interim fixes and populate caches to optimize runtime
RUN configure.sh
