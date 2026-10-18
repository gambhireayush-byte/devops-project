FROM tomcat:10.1-jdk17-temurin
ARG APP_VERSION=1.0.0
LABEL org.opencontainers.image.title="Pharmacy Inventory System" org.opencontainers.image.version="$APP_VERSION"
RUN rm -rf /usr/local/tomcat/webapps/*
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*
COPY target/pharmacy-inventory.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s --start-period=25s --retries=3 CMD curl -fsS http://127.0.0.1:8080/health || exit 1
