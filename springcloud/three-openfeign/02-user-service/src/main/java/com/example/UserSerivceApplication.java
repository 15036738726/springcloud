package com.example;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.example.feign"}) // 开启feign的客户端功能 才可以帮助我们发起调用
public class UserSerivceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserSerivceApplication.class, args);
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 打印fein日志信息 级别
     * @return
     */
    @Bean
    public Logger.Level level(){
        return Logger.Level.FULL;
    }
}
