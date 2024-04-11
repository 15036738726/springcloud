总结：Gateway 的核心逻辑也就是 路由转发 + 执行过滤器链

Route(路由)（重点 和 eureka 结合做动态路由）
Predicate(断言)（就是一个返回 bool 的表达式）
Filter(过滤) (重点)


建议配置动态路由


在 gateway 启动时会去加载一些路由断言工厂(判断一句话是否正确 一个 boolean 表达式 )
https://docs.spring.io/spring-cloud-gateway/docs/2.2.5.RELEASE/reference/html/#g
ateway-request-predicates-factories

断言:断言是给某一个路由来设定的一种匹配规则  具体规则参考官网   ❤❤注意,断言不能作用在动态路由上,断言无效
routes:
    -   id: login-service-route

        uri: lb://login-service
        predicates: # 断言是给某一个路由来设定的一种匹配规则 默认不能作用在动态路由上
            - Path=/doLogin
            - After=2022-03-22T08:42:59.521+08:00[Asia/Shanghai]
            - Method=GET,POST
            - Query=name,admin.   #正则表达式的值


断言和过滤区别:断言是做路径的匹配和转发的 而过滤可以做很多的业务操作


跨域:
ajax 同源策略导致跨域问题
解决办法
1.使用注解@CrossOrigin 标识方法或者类
2.自己配置过滤器,因为网关是微服务的边缘 所有的请求都要走网关 跨域的配置只需要写在网关即可
@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}

3.yml配置
spring:
    cloud:
        gateway:
            globalcors:
                corsConfigurations:
                    '[/**]':
                        allowCredentials: true  # 可以携带cookie
                        allowedHeaders: '*'
                        allowedMethods: '*'
                        allowedOrigins: '*'


