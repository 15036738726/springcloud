package com.example.controller;

import com.example.feign.ProviderFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ConsumerController {

    @Resource
    private ProviderFeign feign;

    @GetMapping("consumer")
    public String consumer() {
        String test = feign.test();
        return test;
    }
}
