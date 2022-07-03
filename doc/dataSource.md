# Spring boot 数据源配置

## 单个数据源配置

使用基于内存的 H2 数据库方便使用。

引入相应依赖，spring boot 自动配置

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

配置：

```yaml
spring:
  dataSource:
    driverClassName: org.h2.Driver
    url: jdbc:h2:mem:testdb
    username: root
  h2:
    console.enabled: true  # 开启 h2 的 web 控制台 /h2-console
```

当 resources 下有 `schema.sql` 和 `data.sql` 时，spring boot 会自动执行，进行库表初始化与数据插入

## 多数据源的配置

多数据源时，不同数据源的所有配置都需要分开。

1. 完全手动配置
2. 结合 spring boot 配置

