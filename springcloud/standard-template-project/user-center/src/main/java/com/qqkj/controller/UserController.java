package com.qqkj.controller;

import com.qqkj.domain.Order;
import com.qqkj.feign.UserOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserOrderFeign userOrderFeign;

    @GetMapping("find")
    public Order findOrder() {
        return userOrderFeign.getOrderByUserId(1);
    }
}
