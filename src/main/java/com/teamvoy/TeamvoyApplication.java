package com.teamvoy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TeamvoyApplication {
    private static final Logger logger = LoggerFactory.getLogger(TeamvoyApplication.class);

    public static void main(String[] args) {
        logger.info("Starting TeamvoyApplication...");
        SpringApplication.run(TeamvoyApplication.class, args);
        logger.info("TeamvoyApplication started successfully.");
    }
}