package com.emsp.chargestation;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
@MapperScan("com.emsp.chargestation.mapper")
public class ChargeStationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChargeStationApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printSwaggerUrl(ApplicationReadyEvent event) {
        try {
            Environment env = event.getApplicationContext().getEnvironment();
            String protocol = env.getProperty("server.ssl.key-store") != null ? "https" : "http";
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            String port = env.getProperty("server.port", "8080");
            String contextPath = env.getProperty("server.servlet.context-path", "");

            String line = "----------------------------------------------------------";
            log.info("\n{}\nApplication is running! Access URLs:", line);
            log.info("Local:     {}://localhost:{}{}/swagger-ui.html", protocol, port, contextPath);
            log.info("External:  {}://{}:{}{}/swagger-ui.html", protocol, hostAddress, port, contextPath);
            log.info("{}\n", line);
        } catch (UnknownHostException e) {
            log.error("Could not determine host address", e);
        }
    }
}