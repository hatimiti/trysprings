package com.github.hatimiti.spring.actuator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SampleHealthIndicator extends AbstractHealthIndicator {

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		final Random random = new Random();
		if (random.nextInt() % 2 == 0) {
			builder.withDetail("counter", "toDown");
			throw new IllegalStateException("status down --");
		}
		builder.withDetail("counter", "toUp");
	}

}
