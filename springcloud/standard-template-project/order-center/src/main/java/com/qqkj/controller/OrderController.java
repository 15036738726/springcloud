package com.qqkj.controller;

import com.qqkj.domain.Order;
import com.qqkj.feign.UserOrderFeign;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements UserOrderFeign {

    /**
     * 这里直接实现  重写内部方法即可  请求路径在feign中已经定义好了
     * @param userId
     * @return
     */
    @Override
    public Order getOrderByUserId(Integer userId) {
        System.out.println(userId);
        Order order = Order.builder()
                .name("青椒肉丝盖饭")
                .price(15D)
                .orderId(2)
                .build();
        return order;
    }
}
