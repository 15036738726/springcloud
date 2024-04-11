package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class NacoaConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacoaConfigApplication.class, args);
    }

}
