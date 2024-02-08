# Build stage
FROM gradle:7.4-jdk17 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle build --no-daemon

# Package stage
FROM openjdk:17-jdk-slim
WORKDIR /usr/local/lib
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
COPY entrypoint.sh /usr/local/bin/entrypoint.sh
COPY wait-for-it.sh /usr/local/bin/wait-for-it.sh

# Establece el puerto en el que se ejecutará la aplicación

EXPOSE 8081


ENTRYPOINT ["sh", "/usr/local/bin/entrypoint.sh"]
