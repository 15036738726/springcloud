package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAController {
    @GetMapping("test")
    public String test(){
        return "test-aaaaaa";
    }
}