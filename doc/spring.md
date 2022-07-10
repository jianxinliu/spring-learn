# SpringBoot Test

使用 Spring Boot 集成测试框架 Junit

组要使用几个注解：

1. `@RunWith()` 是一个运行器，指定具体的执行器，可以填 `SpringRunner`, `SpringJUnit4ClassRunner`, `JUnit4` 等
2. `SpringBootTest` 让测试运行与 Spring 的环境，可以引用 Spring 容器内的 Bean。

在测试类上添加以上注解，类中增加使用 `@Test` 注解的方法即可运行。一个方法表示一个测试用例。