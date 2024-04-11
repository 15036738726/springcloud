package com.example.feign;

import com.example.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @FeignClient(value = "order-service")
 * value 就是提供者的应用名称
 */
@FeignClient(value = "order-service")
public interface UserOrderFeign {

    /**
     * 你需要调用哪个controller  就写它的方法签名
     * 方法签名(就是包含一个方法的所有的属性)
     *
     * @return
     */
    @GetMapping("doOrder")
    String doOrder();

    @GetMapping("testUrl/{name}/and/{age}")
    String testUrl(@PathVariable("name") String name, @PathVariable("age") Integer age);


    @GetMapping("oneParam")
    String oneParam(@RequestParam(required = false,value = "name") String name);

    @GetMapping("twoParam")
    String twoParam(@RequestParam(required = false,value = "name") String name, @RequestParam(required = false,value = "age") Integer age);

    @PostMapping("oneObj")
    String oneObj(@RequestBody Order order);


    @PostMapping("oneObjOneParam")
    String oneObjOneParam(@RequestBody Order order, @RequestParam("name") String name);

    @GetMapping("testTime")
    String testTime(@RequestParam("date") Date date);


    @GetMapping("flectSendTestGet")
    String flectSendTestGet(@RequestParam("name") String name);

//    @PostMapping("flectSendTestPost")
//    String flectSendTestPost(@RequestBody Map<String, Object> map);
}
