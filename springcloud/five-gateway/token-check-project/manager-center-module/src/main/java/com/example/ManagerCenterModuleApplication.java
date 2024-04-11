package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ManagerCenterModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerCenterModuleApplication.class, args);
    }

}
