package com.example.controller;

import com.example.feign.ProviderFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CustomerController {

    @Resource
    private ProviderFeign providerFeign;

    @GetMapping("test")
    public String test(){
        return providerFeign.test();
    }
}
