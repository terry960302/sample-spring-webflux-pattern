# multi-stage build pattern
FROM gradle:7.5.1-jdk18 AS builder

WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle  ./gradle/
COPY src ./src/
RUN gradle clean build --no-daemon

FROM openjdk:18 AS runner

ARG JAR_FILE=app/build/libs/*.jar
ENV PROFILE=dev

COPY --from=builder ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=${PROFILE}", "/app.jar"]