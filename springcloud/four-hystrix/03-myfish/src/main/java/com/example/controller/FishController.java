package com.example.controller;

import com.example.anno.MyFish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@RestController
public class FishController {

    @Autowired
    private RestTemplate restTemplate;

    private Random random = new Random();

    /**
     * 把02-rent-car-service 项目启动起来 模拟
     * @return
     */
    @GetMapping("doRpc")
    @MyFish
    public String doRpc(){
        // 调用不通
        String url = "http://localhost:8888/abc";
        int i = random.nextInt(2);
        // 50%概率调用成功
        if(i==1){
            // 可以调用
            url = "http://localhost:8081/rent";
        }
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }


}
