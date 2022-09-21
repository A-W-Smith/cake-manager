package com.waracle.cakemgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Handles starting the Spring system
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // TODO: Initialise database
        SpringApplication.run(Application.class, args);
    }
}