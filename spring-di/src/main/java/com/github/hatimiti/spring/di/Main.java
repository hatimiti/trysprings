package com.github.hatimiti.spring.di;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

/**
 *
 */
@Configuration
@Import({DIConfig.class})
@ComponentScan
@EnableTransactionManagement(proxyTargetClass = true)
public class Main {
	public static void main(final String[] args) {
		ConfigurableApplicationContext cxt = SpringApplication.run(Main.class, args);

		System.out.println("Context = " + cxt.getClass());
		System.out.println("BeanFactory = " + cxt.getBeanFactory().getClass());
		System.out.println("------------- Beans Start -------------");
		Arrays.asList(cxt.getBeanDefinitionNames()).forEach(System.out::println);
		System.out.println("------------- Beans End -------------");
	}
}
