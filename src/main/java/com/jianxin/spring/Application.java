package com.jianxin.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jianxinliu
 */
@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
		JdbcTemplateAutoConfiguration.class
})
@EnableAutoConfiguration
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
