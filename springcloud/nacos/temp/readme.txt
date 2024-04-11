DiscoveryClent

经过测试,跨命名空间或者跨组 都是不能进行服务之间的发现的

从配置文件中的配置上下级关系 就可以看出来  gateway网关和nacos是平级的关系  都是在spring.cloud下的节点
而gateway是spring官方提供的组件  nacos是阿里提供的组件  他们是平级的关系(虽然nacos作为重要的注册中心)
而eureka则是 netflex公司的产品  它在配置文件中的位置 是单独的
所以SpringCloud  SpringCloudAlibaba 他们是互补的关系  目前没有谁能能完全替代谁

如果要引入springcloud旗下的组件 注意版本的对应关系
https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E
boot:2.3.12.RELEASE
spring-cloud:Hoxton.SR12
spring-cloud-alibaba:2.2.7.RELEASE
目前最新版对应关系
Spring Cloud Alibaba Version	Spring Cloud Version	Spring Boot Version
2.2.10-RC1*                     Spring Cloud Hoxton.SR12    2.3.12.RELEASE



#####################nacos配置文件中心####################################################################################

03-nacos-config这个案例中,只引入了web坐标(用于测试)和nacosConfig坐标(也就是不用向nacos注册,也是可以单独作为一台应用,去nacos拿配置文件的)
一般我们的配置都是读取到一个实体上,注入的容器中,然后使用时,自动注入即可

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RefreshScope  // 给这个类上 添加一个刷新的作用域,当配置文件修改之后,会重新加载被此注解标识的类,达到实时刷新的目的
@Component
public class Hero {
    private String name;
    private Integer age;
    private String address;
}
推荐修改配置文件较高高优先级 bootstrap.yml,因为启动的时候,就会去nacos平台读取配置
nacos配置中心通过namespace group dataId来确定一条唯一的配置
dataId(配置文件名称,在yml文件中通过prefix来指定)
nacosconfig还支持历史文件回滚操作


读取nacos配置中心的文件时,去找那个文件? 名称规则如下:
${prefix}-${spring.profiles.active}.${file-extension}
如果你在项目的配置文件中,指定了spring.profiles.active属性,那么创建文件的时候一定要加上后缀
这个地方应该有bug,经过测试如果不加后缀,就会读取失败,所以,无论有没有配置该属性,文件后缀名都可以加上



读取多个配置文件


读取共享配置文件



# 本地bootstrap.yml  写什么   远端的配置文件写什么?
# 1.应用名称 spring.application.name
# 2.nacos的注册和拉取配置文件

# 远端放 端口 数据源 redis mq 能放远端的全放  因为方便管理和修改 包括自定义配置

#####################nacos配置文件中心####################################################################################

































