package com.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "provider-serivce")
public interface ProviderFeign {

    @GetMapping("test")
    String test() ;

}
