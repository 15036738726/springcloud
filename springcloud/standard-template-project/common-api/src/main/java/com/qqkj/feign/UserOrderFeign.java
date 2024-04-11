package com.qqkj.feign;

import com.qqkj.domain.Order;
import com.qqkj.feign.hystrix.UserOrderFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "order-service",fallback = UserOrderFeignHystrix.class)
public interface UserOrderFeign {

    @GetMapping("order/getOrderByUserId")
    Order getOrderByUserId(@RequestParam("userId") Integer userId);
}
