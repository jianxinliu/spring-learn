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

其中一个数据源的配置。

```java
@Configuration
@Slf4j
public class H2DataSourceConfImpl implements DataSourceConf {
    @Override
    @Primary
    @Bean(name = "h2DataSourceProperties")
    @ConfigurationProperties("spring.datasource.h2")
    public DataSourceProperties getDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Override
    @Primary
    @Bean(name = "h2DataSource")
    public DataSource getDataSource(@Qualifier("h2DataSourceProperties") DataSourceProperties dataSourceProperties) {
        log.info("h2 dataSource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Override
    @Primary
    @Bean(name = "h2JdbcTemplate")
    public JdbcTemplate getJdbcTemplate(@Qualifier("h2DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Override
    public PlatformTransactionManager getTxManager(@Qualifier("h2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
```

注意点：

1. 当有多个数据源时，需要指定一个默认的。使用 `@primary` 指定，或者在使用时显示得指明使用哪个数据源
2. 配置多个数据源时，需要排除 SpringBoot 进行自动配置的类

```java
@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
		JdbcTemplateAutoConfiguration.class
})
```