package com.qqkj.feign.hystrix;

import com.qqkj.domain.Order;
import com.qqkj.feign.UserOrderFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class UserOrderFeignHystrix implements UserOrderFeign {

    /**
     * 一般远程调用的熔断可以直接返回null
     * @param userId
     * @return
     */
    @Override
    public Order getOrderByUserId(Integer userId) {
        //return null;
        return Order.builder().orderId(101).name("备胎").build();
    }
}
