# Build stage
FROM gradle:7.4-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Package stage
FROM openjdk:17-jdk-slim
COPY --from=build /home/gradle/src/build/libs/*.jar /usr/local/lib/
VOLUME /test_vol
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]