package com.github.hatimiti.spring.lifecycle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class LifeCycleTest {

    @BeforeAll
    static void beforeAll() {
    }

    @Test
    public void testDummy() {
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }
}
