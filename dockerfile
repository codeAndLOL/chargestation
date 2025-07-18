FROM eclipse-temurin:8-jre-alpine

# 安装MySQL客户端
RUN apk add --no-cache mysql-client

WORKDIR /app
COPY target/charge-station-0.0.1-SNAPSHOT.jar ./charge-station.jar

# 暴露应用端口（保持与配置一致）
EXPOSE 80

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=test", "/app/charge-station.jar"]