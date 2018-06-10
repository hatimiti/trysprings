package com.github.hatimiti.spring.lifecycle;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 */
@Configuration
@Import({LyfeCycleConfig.class})
@ComponentScan
public class Main {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

		context.getBean(LifeCycleService.class).hello();
		context.getBean(LifeCyclePrototypeService.class).hello();
	}
}