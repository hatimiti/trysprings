package com.github.hatimiti.spring.rest;

import com.github.hatimiti.spring.common.db.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SampleRestControllerTest {

    private static final String BASE_URI = "http://localhost:8080";

    @Autowired
    private RestTemplate restTemplate;

    @BeforeEach
    void before() {
        final User u1 = new User();
        u1.userId = 1L;
        u1.name = "sample1";
        u1.password = "hoge";
        restTemplate.put(BASE_URI + SampleRestController.URI, u1);

        final User u2 = new User();
        u2.userId = 2L;
        u2.name = "sample2";
        u2.password = "fuga";
        restTemplate.put(BASE_URI + SampleRestController.URI, u2);
    }

    @AfterEach
    void after() {
        restTemplate.delete(BASE_URI + SampleRestController.URI);
    }

    @Test
    public void testGetAll() {
        final User[] users = restTemplate.getForObject(
                BASE_URI + SampleRestController.URI, User[].class);
        Assertions.assertEquals(2, users.length);
    }

    @Test
    public void testPost() throws Exception {

        final User u = new User();
        u.userId = 3L;
        u.name = "sample3";
        u.password = "****";

        final RequestEntity<User> request = RequestEntity
                .post(new URI(BASE_URI + SampleRestController.URI))
                .header("User-Agent", "JUnit5/testPut()")
                .accept(MediaType.APPLICATION_JSON)
                .body(u);

        final ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplateBuilder()
                    .additionalInterceptors(loggingInterceptor())
                    .build();
        }

        @Bean
        public LoggingInterceptor loggingInterceptor() {
            return new LoggingInterceptor();
        }

        public static class LoggingInterceptor implements ClientHttpRequestInterceptor {

            private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

            @Override
            public ClientHttpResponse intercept(
                    final HttpRequest request,
                    final byte[] body,
                    final ClientHttpRequestExecution execution) throws IOException {

                LOGGER.info("- Request  Headers: " + request.getHeaders());
                final ClientHttpResponse response = execution.execute(request, body);
                LOGGER.info("- Response Headers: " + response.getHeaders());
                return response;
            }
        }
    }
}
