package com.github.hatimiti.spring.di;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 */
@Configuration
@Import({DIConfig.class})
@ComponentScan
@EnableTransactionManagement(proxyTargetClass = true)
public class Main {
	public static void main(final String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
