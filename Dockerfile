FROM openjdk:18
CMD ["gradlew", "build"]
ARG JAR_FILE=build/libs/*.jar
ENV PROFILE=dev
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=${PROFILE}", "/app.jar"]