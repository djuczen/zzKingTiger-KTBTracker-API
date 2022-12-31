FROM openliberty/open-liberty:full-java17-openj9-ubi

ARG VERBOSE=true
ARG TLS=true

ENV SEC_IMPORT_K8S_CERTS=true

# Add Liberty server configuration including all necessary features
COPY --chown=1001:0  src/main/liberty/config/ /config/
COPY --chown=1001:0  ~/.zprofile /config/server.env
COPY --chown=1001:0  ~/Downloads/ktbtracker-1659484356633-444cd448ac34.json /config/

COPY --chown=1001:0  build/wlp/usr/shared/ /liberty/usr/shared/
# Add app
COPY --chown=1001:0  build/libs/KingTiger-KTBTracker-API-1.0.0.war /config/dropins/

# This script will add the requested server configurations, apply any interim fixes and populate caches to optimize runtime
RUN configure.sh
