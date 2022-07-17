# Spring 中注解

## `@Value`

> Value 注解一般用于注入配置文件中的值到容器中。

### 基本使用方法：

```yaml
test:
  foo: 123
  bar: 456
```

```java
@Component
@Slf4j
public class TestConfig implements CommandLineRunner {

    @Value("${test.foo}")
    private Integer foo;

    @Value("${test.bar}")
    private Integer bar;

    @Override
    public void run(String... args) throws Exception {
        log.info(String.format("config from file: foo: %d, bar:%d", foo, bar));
    }
}
```

注意点：

1. `@Value` 中指定的路径名必须与配置文件中的保持一致。`@ConfigurationProperties` 中可以混用**驼峰式**或者**小写字母加中划线**的方式。



### 设置默认值

`@Value("${some.path:defauleValue}")`。使用 `@Value` 注解时，尽量设置一个默认值，避免使用不同的配置文件时忘记设置值而导致的启动失败问题。



### 给 static 变量注入值

`@Value` 注解默认不支持给静态变量注入值。但可以通过在 `setter` 中给静态变量赋值，再给 `setter` 方法增加 `@Value` 注解来实现。(直接给 static 字段增加 `@Value` 注解，得到的值是 null)

```java
@Component
public class Service {
    private static String staticName;

    @Value("${test.staticName}")
    public void setStaticName(String name) {
        Service.staticName = name;
    }
}
```

### 支持注入的类型

1. 基本类型：即支持注入基本类型，也支持注入其对应的包装类型

2. 数组。`@Value("${test.array:1,2,3,4}")`

3. `List`。不支持直接注入 List，需要使用 EL 表达式对配置值进行调整。`@Value("#{'${test.list}'.split(',')}")`。yml 中需要写作： `test.list: 1,2,3,4,5`

4. `Map`。`@Value("#{${test.cityMap}}")` 。yml 中需要写作：`cityMap: "{ 'jack': 'beijing', 'rose': 'shanghai' }"`



### `#` 与 `$`

- `${...}` 可以获取配置文件中的值，用于指定路径。

- `#{...}` 一般用来使用 EL 表达式，里面的内容需要符合 EL 表达式的语法

二者也可以混用，先用 `$` 取值，再用 `#` 使用 EL 表达式处理。此时必须是 `#` 在外层。

### EL 表达式

同时，使用 EL 表达式也可以注入 Bean，也可以从其他 Bean 中注入成员变量、常量、方法、静态方法获取到的值，到相应的成员变量中。如：

```java
@Service
public class RoleService {
    public static final int DEFAULT_AGE = 18;
    public int id = 1000;

    public String getRoleName() {
        return "管理员";
    }

    public static int getParentId() {
        return 2000;
    }
}


@Service
public class UserService {

    @Value("#{roleService.DEFAULT_AGE}")
    private int myAge;

    @Value("#{roleService.id}")
    private int id;

    @Value("#{roleService.getRoleName()}")
    private String myRoleName;

    @Value("#{roleService.getParentId()}")
    private String myParentId;

    public String test() {
        System.out.println(myAge);
        System.out.println(id);
        System.out.println(myRoleName);
        System.out.println(myParentId);
        return null;
    }
}
```
