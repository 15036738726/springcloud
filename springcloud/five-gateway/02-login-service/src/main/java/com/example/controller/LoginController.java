package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@RestController
public class LoginController {

    @GetMapping("doLogin")
    public String doLogin(String name, String pwd) {
        System.out.println(name);
        System.out.println(pwd);
        String token = UUID.randomUUID().toString();
        return token;
    }


    @GetMapping("myService/a")
    public String a() {
        return "a";
    }
    @GetMapping("myService/b")
    public String b() {
        return "b";
    }

}
