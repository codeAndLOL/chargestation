# Dockerfile
FROM openjdk:8-jre-alpine
COPY target/charging-station.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]