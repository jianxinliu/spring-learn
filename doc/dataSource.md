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

1. 当有多个数据源时，需要指定一个默认的。使用 `@Primary` 指定，或者在使用时显示得指明使用哪个数据源
2. 配置多个数据源时，需要排除 SpringBoot 进行自动配置的类

```java
@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
		JdbcTemplateAutoConfiguration.class
})
```

## Spring 中的 事务

### 事务抽象的核心接口：
1. PlatformTransactionManager (不同组件的事务管理器抽象，DataSourceTransactionManager、HibernateTransactionManager……)
2. TransactionDefinition

### 手动操作事务

使用 `TransactionTemplate`.

```java
@Component
@Slf4j
public class TransactionTest implements CommandLineRunner {

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource(name = "h2JdbcTemplate")
    private JdbcTemplate h2JdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // 事务开始前查询条数
        log.info("count before tx: {}", getCount());
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                // 插入数据
                for (int i = 0; i < 10; i++) {
                    h2JdbcTemplate.execute(String.format("insert into student(sno, city) values(%d, 'jack')", 9999 + i));
                }
                // 事务进行中查询条数
                log.info("count in tx: {}", getCount());
                
                // 事务执行，此时事务结束后，插入成功，条数 + 1
                status.flush();
                
                // 事务回滚，此时事务结束后，插入取消，条数不变
//                status.setRollbackOnly();
            }
        });
        // 事务结束后查询条数
        log.info("count after tx: {}", getCount());
    }

    private Long getCount() {
        return (Long) h2JdbcTemplate.queryForList("select count(1) as cnt from student").get(0).get("cnt");
    }
}
```

### 使用注解管理事务

使用 `@Transactional`.

```java
@Transactional(rollbackFor = Exception.class)
public void insertThenRollback() throws Exception {
    h2JdbcTemplate.execute(String.format("insert into student(sno, city) values(%d, 'jack')", 9998));
    throw new Exception("");
}
```

需要注意的是：

直接调用带有 `@Transactional` 的方法，并不会开启事务。因为 spring 的声明式事务是使用 aop 代理实现的，开启事务的前提是方法被代理调用，这种内部调用的方式并没有走代理，所以不会开启事务。就算抛异常，也不会回滚

```java
public void invokeInsertThenRollback() throws Exception {
    insertThenRollback();
}
```

解决办法是：

1. 给该方法也加上 `@Transactional`（推荐）
2. 获取代理对象，用该代理对象调用带 `@Transactional` 的方法。`AopContext.currentProxy()` 或 `ApplicationContext.getBean`
3. 注入对象自己，使用该对应调用替换直接的方法调用 （次推荐）

实际上，这不仅仅是事务这个注解才有的问题，而是 Spring 的代理机制决定的。换成别的方法上的注解，也是同样的原因，同样的解决办法

## Spring JDBC 的异常抽象

Spring 会将数据操作的异常转换为 `DataAccessException`，无论何种访问数据的方式，都能使用同样的异常

