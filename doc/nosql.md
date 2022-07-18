# Redis

## 使用 Jedis 访问 redis

pom:

```xml
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>
```

配置文件：

```yaml
redis:
  host: localhost
  maxIdle: 5
  maxTotal: 5
  testOnBorrow: true
```

config:

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisPoolConf {
    @Bean
    @ConfigurationProperties(prefix = "redis")
    public JedisPoolConfig getJedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean
    public JedisPool jedisPool(@Value("${redis.host}") String host) {
        return new JedisPool(getJedisPoolConfig(), host);
    }
}
```

测试使用：

```java
import com.jianxin.spring.repository.StudentRepository;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Service
public class StudentService {
    @Resource
    private StudentRepository studentRepository;

    @Resource
    private JedisPool jedisPool;

    public Stiring findCityByName(String name) {
        try (JedisPool jedis  = jedisPool.getResource()) {
            String key = "stu_" + name;
            Boolean exists = jedis.exists(key);
            if (exists) {
                log.info("hit cache: {}", key);
                return jedis.get(key);
            } else {
                Student stu = studentRepository.findByName(name);
                jedis.set(key, stu.getCity());
                return stu.getCity();
            }
        }
    }
}
```

## Spring 中的缓存抽象

为不同的缓存提供一层抽象

- 为 Java 方法增加缓存，缓存执行的结果
- 缓存支持 ConcurrentMap, EhCache, Caffeine, JCache, Redis
- 接口：
  - org.springframework.cache.Cache
  - org.springframework.cache.CacheManager

使用简单 cache (JVM)

1. 使用 `@EnableCaching(proxyTargetClass = true)` 开始缓存支持， 并开启代理
2. 缓存

```java
@Service
@CacheConfig(cacheNames = "student")
public class SimpleCache {

    @Resource
    private StudentRepository studentRepository;

    @Cacheable
    public String getCityByName() {
        return studentRepository.findByName("jack").getCity();
    }

    /**
     * 清理缓存
     */
    @CacheEvict
    public void reload() {}
}
```

使用 Redis 作为缓存，只需要做一些配置调整，而不需要调整代码，这就是缓存抽象的好处。