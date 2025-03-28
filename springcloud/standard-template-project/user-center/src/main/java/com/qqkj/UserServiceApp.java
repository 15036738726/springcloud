package com.qqkj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.qqkj.feign"})
public class UserServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class,args);
    }
}