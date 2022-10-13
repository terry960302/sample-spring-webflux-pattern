# multi-stage build pattern
FROM gradle:7.5.1-jdk18 AS builder
MAINTAINER terry960302@gmail.com

WORKDIR /app
COPY ["build.gradle.kts", "settings.gradle.kts", "./"]
COPY gradle  ./gradle/
COPY src ./src/
RUN gradle clean build --no-daemon

FROM openjdk:18 AS runner

ENV VERSION=0.0.1-SNAPSHOT
ENV PROFILE=dev

ARG FILENAME=spring-r2dbc-sample-${VERSION}
ARG JAR_FILE=/app/build/libs/${FILENAME}.jar
COPY --from=builder ${JAR_FILE} ./app.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=${PROFILE}", "/app.jar"]