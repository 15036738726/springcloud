package com.example;

import com.example.controller.UserController;
import com.example.domain.Order;
import com.example.feign.UserOrderFeign;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 手写feign的核心步骤
     */
    @Test
    void contextLoads() {
        UserOrderFeign o = (UserOrderFeign) Proxy.newProxyInstance(UserController.class.getClassLoader(), new Class[]{UserOrderFeign.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 通过method去拿服务名和path 然后通过被ribbon托管了的restTemplate进行调用即可
                // http://serviceName/path

                GetMapping annotation = method.getAnnotation(GetMapping.class);
                String[] paths = annotation.value();
                String path = paths[0];
                Class<?> aClass = method.getDeclaringClass();
                FeignClient annotationFeignClient = aClass.getAnnotation(FeignClient.class);
                String serviceName = annotationFeignClient.value();
                String url = "http://" + serviceName + "/" + path;
                String forObject = restTemplate.getForObject(url, String.class);
                return forObject;
            }
        });
        // 调用doOrder方法 -> 执行invoke方法  method就是doOrder方法本身 可以通过该参数反射获取想要的数据
        String s = o.doOrder();
        System.out.println(s);
    }

    /**
     *     反射传参测试
     *
     *     @GetMapping("flectSendTestGet")
     *     String flectSendTestGet(@RequestParam("name") String name);
     *
     *     @PostMapping("flectSendTestPost")
     *     String flectSendTestPost(@RequestBody Order order);
     */
    @Test
    void flectSendTestGet() {
        UserOrderFeign o = (UserOrderFeign) Proxy.newProxyInstance(UserController.class.getClassLoader(), new Class[]{UserOrderFeign.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                GetMapping annotation = method.getAnnotation(GetMapping.class);
                String[] paths = annotation.value();
                String path = paths[0];
                Class<?> aClass = method.getDeclaringClass();
                FeignClient annotationFeignClient = aClass.getAnnotation(FeignClient.class);
                String serviceName = annotationFeignClient.value();
                String url = "http://" + serviceName + "/" + path+"?name="+(String) args[0];
                String forObject = restTemplate.getForObject(url, String.class);
                return forObject;
            }
        });
        String s = o.flectSendTestGet("flectSendTestGet");
        System.out.println(s);
    }

//    @Test
//    void flectSendTestPost() {
//        UserOrderFeign o = (UserOrderFeign) Proxy.newProxyInstance(UserController.class.getClassLoader(), new Class[]{UserOrderFeign.class}, new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                GetMapping annotation = method.getAnnotation(GetMapping.class);
//                String[] paths = annotation.value();
//                String path = paths[0];
//                Class<?> aClass = method.getDeclaringClass();
//                FeignClient annotationFeignClient = aClass.getAnnotation(FeignClient.class);
//                String serviceName = annotationFeignClient.value();
//                String url = "http://" + serviceName + "/" + path;
//                String forObject = restTemplate.postForObject(url,args[0],String.class);
//                return forObject;
//            }
//        });
//        Map<String, Object> map = new HashMap<>();
//        map.put("id",1);
//        map.put("name", "zhangsan");
//        String s = o.flectSendTestPost(map);
//        System.out.println(s);
//    }

}
