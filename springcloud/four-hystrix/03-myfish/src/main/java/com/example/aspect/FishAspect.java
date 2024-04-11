package com.example.aspect;


import com.example.model.Fish;
import com.example.model.FishStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
@Aspect
public class FishAspect {
// 拦截指定类的指定方法
//    public static final String POINT_CUT = "execution (* com.powernode.controller.FishController.doRpc(..))";
// 拦截指定类的所有方法
//    public static final String POINT_CUT = "execution (* com.powernode.controller.FishController.*(..))";

    // 因为一个消费者可以去调用多个提供者  每个提供者都有自己的断路器
    // 在消费者里面去创建一个断路器的容器
    public static Map<String, Fish> fishMap = new HashMap<>();

    static {
        // 假设 是需要去调用order-service的服务
        fishMap.put("order-service", new Fish());
    }


    Random random = new Random();

    // 切点
    @Pointcut("@annotation(com.example.anno.MyFish)")
    public void pointCut(){

    };

    /**
     * 这个就类比拦截器
     * 就是要判断 当前断路器的状态 从而决定是否发起调用(执行目标方法)
     *
     * @param joinPoint
     * @return
     */
    @Around(value = "pointCut()")
    public Object fishAround(ProceedingJoinPoint joinPoint) {
        Object result = null;
        // 获取到当前提供者的断路器
        Fish fish = fishMap.get("order-service");

        FishStatus status = fish.getStatus();
        switch (status) {
            case CLOSE:
                // 正常  去调用 执行目标方法
                System.out.println("熔断器状态:"+"关闭");
                try {
                    result = joinPoint.proceed();
                    return result;
                } catch (Throwable throwable) {
                    // 说明调用失败  记录次数
                    fish.addFailCount();
                    return "我是备胎";
                }
            case OPEN:
                System.out.println("熔断器状态:"+"开");
                // 不能调用
                return "我是备胎";
            case HALF_OPEN:
                System.out.println("熔断器状态:"+"半开");
                // 可以用少许流量去调用
                int i = random.nextInt(5);
                System.out.println(i);
                // 0,1,2,3,4, 5个数 随机==其中一个  20%概率
                if (i == 1) {
                    // 去调用
                    try {
                        result = joinPoint.proceed();
                        // 说明成功了 断路器关闭
                        fish.setStatus(FishStatus.CLOSE);
                        System.out.println("熔断器状态:"+"半开-关闭");
                        // 重置次数
                        fish.getCurrentFailCount().set(0);
                        synchronized (fish.getLock()) {
                            fish.getLock().notifyAll();
                        }
                        return result;
                    } catch (Throwable throwable) {
                        return "我是备胎";
                    }
                }
            default:
                return "我是备胎";
        }
    }
}