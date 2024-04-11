package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscoveryController {

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 服务发现测试
     * 启动client-a和client-b两个客户端,
     * 然后通过传入b的服务名称,找到b实例的全部内容 通过discoveryClient对象
     * (主要是ip,port 这样就可以发送http请求了)
     * localhost:8080/test?serviceId=eureka-client-b
     * @param serviceId
     * @return
     */
    @GetMapping("test")
    public String discovery(String serviceId){
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        instances.forEach(System.out::println);
        ServiceInstance serviceInstance = instances.get(0);
        String ip = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        System.out.println(ip+":"+port);
        // 这里去找b的ip和port
        return instances.get(0).toString();
    }
    /**
     * [EurekaDiscoveryClient.EurekaServiceInstance@146d9ecc instance = InstanceInfo [instanceId = localhost:eureka-client-b:8081, appName = EUREKA-CLIENT-B, hostName = localhost, status = UP, ipAddr = 192.168.100.1, port = 8081, securePort = 443, dataCenterInfo = com.netflix.appinfo.MyDataCenterInfo@78a0e85c]
     * localhost:8081
     */
}
