package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerCenterController {

    @GetMapping("center")
    public String center(){
        return "欢迎来到管理中心";
    }
}
