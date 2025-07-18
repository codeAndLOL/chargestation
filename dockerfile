FROM eclipse-temurin:8-jre-alpine

# 安装MySQL客户端（用于健康检查）
RUN apk add --no-cache mysql-client

WORKDIR /app
COPY target/charge-station-*.jar ./charge-station.jar

# 暴露应用端口（与你的application-test.yml配置一致）
EXPOSE 80

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=test", "/app/charge-station.jar"]