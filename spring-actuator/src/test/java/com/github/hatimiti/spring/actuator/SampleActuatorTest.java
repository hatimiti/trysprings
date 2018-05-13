package com.github.hatimiti.spring.actuator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SampleActuatorTest {

	@Autowired TestRestTemplate restTemplate;

	@Test
	public void testActuatorEndpoints() {
		String result = restTemplate.getForObject("/actuator", String.class);
		Assertions.assertThat(result).contains("/beans", "/shutdown");
	}

	@Test
	public void testActuatorSampleInfoContributor() {
		String result = restTemplate.getForObject("/actuator/info", String.class);
		assertEquals("{\"example\":{\"someKey\":\"someValue\"}}", result);
	}

	@Test
	public void testActuatorHelloHealthIndicator() {
		String result = restTemplate.getForObject("/actuator/health", String.class);
		Assertions.assertThat(result).contains("\"hello\":{\"status\":\"UP\",\"details\":{\"hello\":\"world\"}}");
	}

	@Test
	public void testActuatorSampleHealthIndicator() {
		String result = restTemplate.getForObject("/actuator/health", String.class);
		Assertions.assertThat(result).containsPattern("(toUp|toDown)");
	}

	@Test
	public void testActuatorSampleRestControllerEndpoint() {
		String result = restTemplate.getForObject("/actuator/example/echo/hoge", String.class);
		assertEquals("hoge", result);
	}
}
