# SpringBoot
由于SpringBoot自动内置了servlet容器,所以不需要类似传统的方式,先部署到容器再启动容器.只需要运行main()即可,

SpringBoot优点:
你没有做任何的web.xml配置.
你没有做任何的sping mvc的配置; SpringBoot为你做了.
你没有配置tomcat,SpringBoot内嵌tomcat.

# SpringBoot-config
修改 SpringBoot 自定义配置
1. 自定义属性
在 application.yml 中自定义一组属性,web.MiyaController 中若需要读取配置文件的值只需要加 @Value("${属性名}")
localhost:8082/miya

2. 将配置文件的属性赋给实体类
application.yml 中 ${random},它可以用来生成各种不同类型的随机值,在 ConfigBean 中使用.
在 javabean 上使用 @ConfigurationProperties 注解加上 prefix,来将配置文件中的值注入到bean中(参见bean.ConfigBean的配置).
另外需要在应用类或者 application 类,如 LucyController 中,加 @EnableConfigurationProperties 注解.
http://localhost:8082/lucy

3. 自定义配置文件
如 test.properties.
将这个配置文件信息赋予给一个javabean.
需要加这三个注解,如bean.User类的配置.
```java
@Configuration
@PropertySource(value = "classpath:test.properties")
@ConfigurationProperties(prefix = "com.zc")
```
PropertySource加上location.
在LucyController中使用.
localhost:8082/user

4. 多个环境配置文件
需要不同的配置环境；格式为 application-{profile}.properties,其中{profile}对应你的环境标识,比如：
application-test.properties：测试环境
application-dev.properties：开发环境
application-prod.properties：生产环境

在application.yml中加
 spring:
  profiles:
    active: dev #当前使用dev环境

其中application-dev.yml:
  server:
   port: 8082

启动工程,发现程序的端口不再是8080,而是8082

# SpringBoot-jdbc
SpringBoot 用 JdbcTemplates 访问 Mysql.

0.建表准备数据
```mysql
-- create table `account`
DROP TABLE `account` IF EXISTS;

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `money` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `account` VALUES ('1', 'aaa', '1000');
INSERT INTO `account` VALUES ('2', 'bbb', '1000');
INSERT INTO `account` VALUES ('3', 'ccc', '1000');
```

1.引入依赖：
在pom文件引入spring-boot-starter-jdbc的依赖：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

引入mysql驱动和连接池
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.0.29</version>
</dependency>

开启web
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

2.配置相关文件
在 application.properties 文件配置 mysql 的连接信息.
```
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=126200
```

3.rest接口访问
http://localhost:8080/account/list

# SpringBoot整合mybatis
SpringBoot-mybatis模块
1.引入依赖
在pom文件引入mybatis-spring-boot-starter的依赖;
引入数据库连接依赖jar;
引入数据源配置.

2.通过注解的形式实现
dao层

http://localhost:8080/account/list

# SpringBoot开启声明式事务
springboot-mybatis-tx模块
service层添加@Transactional
@Transactional,声明事务,并设计一个转账方法,用户1减10块,用户2加10块.在用户1减10 ,之后,抛出异常,即用户2加10块钱不能执行,
当加注解@Transactional之后,两个人的钱都没有增减.当不加@Transactional,用户1减了10,用户2没有增加,即没有操作用户2 的数据.
可见@Transactional注解开启了事务.

http://localhost:8080/account/list

# SpringBoot整合Redis
springboot-redis模块
在pom文件中添加redis依赖：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

```
配置数据源
spring.redis.host=localhost
spring.redis.port=6379
#spring.redis.password=
spring.redis.database=1
spring.redis.pool.max-active=8
spring.redis.pool.max-wait=-1
spring.redis.pool.max-idle=500
spring.redis.pool.min-idle=0
spring.redis.timeout=0
```

2.使用
dao层通过redisTemplate来访问redis.

3.运行SpringbootRedisApplication,查看console输出.