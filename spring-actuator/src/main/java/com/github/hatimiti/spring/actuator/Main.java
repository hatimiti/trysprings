package com.github.hatimiti.spring.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

	@Bean
	public HealthIndicator helloHealthIndicator() {
		return new HealthIndicator() {
			@Override
			public Health health() {
				return Health.up().withDetail("hello", "world").build();
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
