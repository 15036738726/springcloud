通过网关 对某个路由 进行限流

Spring Cloud Gateway 已经内置了一个 RequestRateLimiterGatewayFilterFactory，我们
可以直接使用。
目前 RequestRateLimiterGatewayFilterFactory 的实现依赖于 Redis，所以我们还要引入
spring-boot-starter-data-redis-reactive。

<!--限流要引入 Redis-->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-redis-reactive</artifactId>
</dependency>



需要配置redis ,因为限流就是借助redis完成的(往redis中设置了令牌数据)


1. IP 限流（5s 内同一个 ip 访问超过 3 次，则限制不让访问，过一段时间才可继续访问）
2. 请求量限流（只要在一段时间内(窗口期)，请求次数达到阀值，就直接拒绝后面来的访问了，
过一段时间才可以继续访问）（粒度可以细化到一个 api（url），一个服务）

创建两个服务,一个网关服务,一个测试服务,访问网关,路由到对应的测试服务上,观察限流效果


Spring Cloud Gateway 已经内置了一个 RequestRateLimiterGatewayFilterFactory，我们
可以直接使用。
目前 RequestRateLimiterGatewayFilterFactory 的实现依赖于 Redis，所以我们还要引入
spring-boot-starter-data-redis-reactive。



该过滤器需要配置三个参数：
burstCapacity：令牌桶总容量。
replenishRate：令牌桶每秒填充平均速率。
key-resolver：用于限流的键的解析器的 Bean 对象的名字。它使用 SpEL 表达式根据
#{@beanName}从 Spring 容器中获取 Bean 对象。


@Primary注解是Spring框架中的一个重要注解，用来标识一个bean是首选的，
在使用自动装配时，如果存在多个类型相同的bean，Spring会优先选择标记为@Primary的bean进行注入


快速访问http://localhost/test 可以看到有时候访问被限制了







