【Spring Cloud Sleuth】

链路追踪 [监控调用过程]


官网：https://spring.io/projects/spring-cloud-sleuth
链路追踪就是：追踪微服务的调用路径


个人认为  用处不大  后边的admin监控比这个要实用的多

http://localhost:9411/


zipkin使用:

启动zipkin-server-2.12.9-exec.jar

所有要跟踪的应用导入
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>

配置文件修改
spring:
    zipkin:
        base-url: http://localhost:9411
    sleuth:
        sampler:
            probability: 1  #配置采样率  默认的采样比例为: 0.1，即 10%，所设置的值介于 0 到 1 之间，1 则表示全部采集
            rate: 10   #为了使用速率限制采样器,选择每秒间隔接受的trace量，最小数字为0，最大值为2,147,483,647（最大int） 默认为10。

启动项目,发起远程调用,观察面板












