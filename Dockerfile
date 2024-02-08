# Build stage
FROM gradle:7.4-jdk17 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle build --no-daemon

# Package stage
FROM openjdk:17-jdk-slim
WORKDIR /usr/local/lib
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Wait for MySQL to be ready
HEALTHCHECK --interval=30s --timeout=10s CMD curl -f http://localhost:3306 || exit 1

# Set the command to run the Java application
CMD ["java", "-jar", "app.jar"]

# Set the port on which the application will run
EXPOSE 8081
