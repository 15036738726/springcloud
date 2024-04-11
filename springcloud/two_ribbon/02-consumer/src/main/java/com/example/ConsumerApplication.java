package com.example;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    /**
     * 这个RestTemplate 已经变了
     * LoadBalanced 他就会被ribbon来操作
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    /**
     * 往容器中放一个rule对象
     * 你访问任何一个提供者 都是这个算法
     * @return
     */
//    @Bean
//    public IRule myRule(){
//        return new RandomRule();// 随机算法
//        return new ZoneAvoidanceRule();// 默认算法
//        // 或者自己实现rule接口,重写choose方法,定义负载策略
//        //return  new MyRule();
//    }


}
