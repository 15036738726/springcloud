
Hoxton.SR12
2.3.12.RELEASE

<spring-boot-admin.version>2.3.0</spring-boot-admin.version>
使用admin时一定要指定2.3.0
这3个版本是匹配的  如果不指定  启动报错



一共2步:
将要被监控的服务添加如下依赖

<!--  暴露自身检查端点 endPoints 一个依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

然后配置文件中,暴露端口即可
management:
    endpoints:
        web:
            exposure:
                include: '*'  # 暴露所有的监控端点 # 如果一个服务需要被监控 那么就要讲自身的一些情况(一些信息接口)暴露出去


被监控的项目
在autuator下的mappings下,会把当前项目以及源码中的所有映射的关系都罗列出来(包括引入的jar包中的) 而这个功能是全局搜索(ctrl shift r)做不到的(jar包中的关键字,全局搜索做不到)


拿到当前项目所有bean对象
http://ip:端口/actuator/beans
