package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TestLoginServcieApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestLoginServcieApplication.class, args);
    }

}
