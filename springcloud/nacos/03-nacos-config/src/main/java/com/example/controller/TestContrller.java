package com.example.controller;

import com.example.domain.Hero;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestContrller {

    @Resource
    private Hero hero;

    @GetMapping("out")
    public String out(){
        return hero.getName()+","+hero.getAge()+","+hero.getAddress();
    }
}
