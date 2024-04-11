package com.example.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "provider-server")
public interface ProviderFeign {


    @GetMapping("test")
    public String test();
}
