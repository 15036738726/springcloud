package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitTestController {

    @GetMapping("test")
    public String test() {
        return "ok";
    }
}
