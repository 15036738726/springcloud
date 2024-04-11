package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 这个demo中通过ribbon解决了两件事
     * 1.restTemplate交给ribbon托管之后,可以直接通过服务名进行系统访问
     * 2.请求时,如果存在多台机器,自动采用了负载均衡(默认轮训算法)
     */


    /**
     * 思考 ribbon是怎么将 http://provider/hello 路径请求成功的
     * http://127.0.0.1:8080/hello
     * 1.拦截这个请求
     * 2.截取主机名称
     * 3.借助eureka来做服务发现 list<>
     * 4.通过负载均衡算法 拿到一个服务ip port
     * 5.reConstructURL
     * 6.发起请求
     *
     * @param serviceName
     * @return
     * http://localhost:8080/testRibbon?serviceName=provider
     */
    @GetMapping("testRibbon")
    public String testRibbon(String serviceName){
        // 正常来讲 需要 拿到ip和port 以及 路径 才可以用
        // http://provider/hello
        String result = restTemplate.getForObject("http://" + serviceName + "/hello", String.class);
        // 只要你给restTemplate 加了ribbon的注解 项目中这个对象发起的请求 都会走ribbon的代理
        // 如果你想使用原生的restTemplate 就需要重新创建一个对象
//        RestTemplate myRest = new RestTemplate();
//        String forObject = myRest.getForObject("http://localhost:8888/aaa", String.class);
        return result;
    }
    /**
     * 先通过 "http://" + serviceId + "/info" 我们思考 ribbon 在真正调用之前需要做什么？
     * restTemplate.getForObject(“http://provider/info”, String.class);
     * 想要把上面这个请求执行成功，我们需要以下几步
     * 1. 拦截该请求；
     * 2. 获取该请求的 URL 地址:http://provider/info
     * 3. 截取 URL 地址中的 provider
     * 4. 从服务列表中找到 key 为 provider 的服务实例的集合(服务发现)
     * 5. 根据负载均衡算法选出一个符合的实例
     * 6. 拿到该实例的 host 和 port，重构原来 URL 中的 provider
     * 7. 真正的发送 restTemplate.getForObject(“http://ip:port/info”，String.class)
     */


    // 轮训的算法 怎么去实现
    // 两台机器   A B
    // A
    // B
    // A
    // B
    // 代码实现轮训的算法  List<机器>
    // 请求次数  请求次数%机器数量
    //  int index =   1 % size    list.get(index);
    // % 取模 取余好处是一个周期函数 让得到的结果 总是小于 除数的
    //  1 / 2    1 % 2
    // 1%2=1
    // 2%2=0
    // 3%2=1
    // 4%2=0
    // 全局顶一个int i = 0
    // i++  线程不安全的
    // i % size
    // 怎么能做一个线程安全的轮训算法   加锁 效率极低  CAS 自旋锁 没有线程的等待和唤醒的开销
    // CAS 优点 性能好 java层面无锁的状态  但是在jvm层面 有锁的cmpxchg
    // CAS 缺点 会导致短暂时间内 CPU 飙升  还有ABA 问题


    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 核心是负载均衡
     * @param serviceName
     * @return
     * http://localhost:8080/testRibbonRule?serviceName=provider
     */
    @GetMapping("testRibbonRule")
    public String testRibbonRule(String serviceName){
        ServiceInstance choose = loadBalancerClient.choose(serviceName);
        return choose.toString();
    }

    /**
     *     从机器列表中选择一个
     *     public Optional<Server> chooseRoundRobinAfterFiltering(List<Server> servers, Object loadBalancerKey) {
     *         List<Server> eligible = getEligibleServers(servers, loadBalancerKey);
     *         if (eligible.size() == 0) {
     *             return Optional.absent();
     *         }
     *         return Optional.of(eligible.get(incrementAndGetModulo(eligible.size())));
     *     }
     *
     *     返回取模下标并且完成累加操作
     *     private int incrementAndGetModulo(int modulo) {
     *         for (;;) {
     *             int current = nextIndex.get();
     *             int next = (current + 1) % modulo;
     *             if (nextIndex.compareAndSet(current, next) && current < modulo)
     *                 return current;
     *         }
     *     }

     *         次数累加 i++ , cas自选处理请求次数累加
     *
     */


}
