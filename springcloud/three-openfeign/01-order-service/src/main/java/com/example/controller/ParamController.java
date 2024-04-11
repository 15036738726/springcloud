package com.example.controller;

import com.example.domain.Order;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * url传参    /doOrder/热干面/add/油条/aaa
 * get传递一个参数
 * get传递多个参数
 * post传递一个对象
 * post传递一个对象+一个基本参数
 */
@RestController
public class ParamController {

    // http://localhost:8081/testUrl/zhangsan/and/1
    @GetMapping("testUrl/{name}/and/{age}")
    public String testUrl(@PathVariable("name") String name, @PathVariable("age") Integer age) {
        System.out.println(name + ":" + age);
        return "ok";
    }

    /**
     * 这个地方很奇怪,网上也有人说,不加value碰到不报错的情况
     * RequestParam.value() was empty on parameter 0 问题解决
     * https://blog.csdn.net/qq_27271817/article/details/111385810
     * 跟版本有关  所以还是都加上吧
     * @param name
     * @return
     */
    @GetMapping("oneParam")
    String oneParam(@RequestParam(required = false,value = "name") String name){
        System.out.println(name);
        return "ok";
    }


    @GetMapping("twoParam")
    public String twoParam(@RequestParam(required = false,value = "name") String name, @RequestParam(required = false,value = "age") Integer age) {
        System.out.println(name);
        System.out.println(age);
        return "ok";
    }

    @PostMapping("oneObj")
    public String oneObj(@RequestBody Order order) {
        System.out.println(order);
        return "ok";
    }


    @PostMapping("oneObjOneParam")
    public String oneObjOneParam(@RequestBody Order order,@RequestParam("name") String name) {
        System.out.println(name);
        System.out.println(order);
        return "ok";
    }

    ////////////////// 单独传递时间对象
    @GetMapping("testTime")
    public String testTime(@RequestParam("date")  Date date){
        System.out.println(date);
        return "ok";
    }

    /**
     * 手写feign的核心步骤时 com.example.ApplicationTests#contextLoads()
     * 传参测试
     * @param name
     * @return
     */
    @GetMapping("flectSendTestGet")
    public String flectSendTestGet(@RequestParam("name") String name){
        System.out.println(name);
        return "ok";
    }

//    @PostMapping("flectSendTestPost")
//    public String flectSendTestPost(@RequestBody Map<String, Object> map){
//        System.out.println(map);
//        return "ok";
//    }


}
