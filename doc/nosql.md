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