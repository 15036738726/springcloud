package com.example.contoller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DiscoveryContrller {
    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * 通过服务名 进行服务发现测试
     * @param serviceId
     * @return
     */
    @GetMapping("test")
    public String test(String serviceId){
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        return "ok";
    }
}
