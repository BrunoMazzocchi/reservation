# Build stage
FROM gradle:7.4-jdk17 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle build --no-daemon

# Package stage
FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y default-mysql-client
RUN apt-get update && apt-get install -y netcat
WORKDIR /usr/local/lib
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
COPY wait-for-mysql.sh wait-for-mysql.sh
RUN bash -c 'chmod +x /usr/local/lib/wait-for-mysql.sh'
ENTRYPOINT ["/usr/local/lib/wait-for-mysql.sh", "mysql", "3306", "java", "-jar", "app.jar"]
# Establece el puerto en el que se ejecutará la aplicación
EXPOSE 8081